package org.scanl.plugins.tsdetect.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.intellij.openapi.project.ProjectManager;
import net.minidev.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.scanl.plugins.tsdetect.common.PluginResourceBundle;
import org.scanl.plugins.tsdetect.config.PluginSettings;

import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

public class AnonymousData {
    private HashMap<String, String> data;

    public AnonymousData() {
        data = new HashMap<>();
    }

    /**
     * Insert a key value pair into the user data map
     * @param key String - "uuid", "timestamp", or test smell name
     * @param value String - UUID value, timestamp, or test smell values
     */
    public void addData(String key, String value) {
        data.put(key, value);
    }

    /**
     * The primary function for gathering and sending data to the server.
     * Attempt to send unsent data followed by current data. If any data is unable to be
     * sent to the server, store it in a new JSON file.
     */
    public void sendData() {
        //only attempt to send the data if the user has opted in
        if (PluginSettings.GetSetting("OPT_IN")) {
            //send previous run data
            sendOldData();

            addData("uuid", PluginSettings.uuid());
            addData("timestamp", new Timestamp(System.currentTimeMillis()).toString());
            try { //send current run data
                JSONObject jsonWrap = new JSONObject(this.data);
                postRequest(jsonWrap.toJSONString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Check if an unsent data file exists. If so, read in each line
     * and send a post request if the date of the data is less than
     * one month old. Ignore data older than one month.
     */
    public void sendOldData() {
        try {
            //get unsent data file
            File dataFile = new File(ProjectManager.getInstance().getOpenProjects()[0].getBasePath() + "/unsentAnonymousData.json");
            if (dataFile.exists()) {
                Scanner scanner = new Scanner(dataFile);

                //check date of data and send it
                List<String> oldData = new ArrayList<>();
                while(scanner.hasNextLine()) {
                    oldData.add(scanner.nextLine());
                }

                //delete old data file
                dataFile.delete();

                for (String jsonString : oldData) {
                    if (dataOlderThanOneMonth(jsonString)) continue;
                    postRequest(jsonString);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * HTTP POST request to the server. Attempt to send the data for a maximum of
     * 5 tries. A 200 status code in the response indicates data was successfully
     * received by the server and the function exits. In the event data was not
     * successfully sent after 5 tries, the data is saved locally.
     * @param jsonString - String of JSON data to be sent. Sending old data requires
     *                     this parameter to be a string.
     */
    public void postRequest(String jsonString) {
        try {
            //attempt to send the data 5 times
            for (int i = 0; i < 5; i++) {
                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpPost post = new HttpPost(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "PLUGIN.DATA.HOSTNAME"));
                post.setHeader("Content-Type", "application/json");
                post.setHeader("Connection", "close");
                BasicHttpEntity httpEntity = new BasicHttpEntity();
                httpEntity.setContent(new ByteArrayInputStream(jsonString.getBytes()));
                post.setEntity(httpEntity);

                HttpResponse httpResponse = httpClient.execute(post);
                //exit on success
                if (httpResponse.getStatusLine().toString().contains("200")) return;
            }
            //save the data locally
            localSave(jsonString);
        } catch (Exception e) {
            localSave(jsonString);
            e.printStackTrace();
        }
    }

    /**
     * Timestamp checker to determine if the data is older than one month.
     * @param jsonString the string of the JSON data
     * @return true/false timestamp is before one month ago
     */
    public boolean dataOlderThanOneMonth(String jsonString) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        String timestamp = jsonObject.get("timestamp").getAsString();
        LocalDate dateTime = LocalDate.parse(timestamp.split(" ")[0]);
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        return dateTime.isBefore(oneMonthAgo);
    }

    /**
     * Saves data locally in a JSON file located in the base of the project directory.
     * Only saves data if the timestamp is within one month of now and the length of
     * the file does not exceed 10,000 lines
     * @param jsonString the string of JSON data to be saved
     */
    public void localSave(String jsonString) {
        //saves data that was unable to be sent after 5 tries
        try {
            File file = new File(ProjectManager.getInstance().getOpenProjects()[0].getBasePath(), "unsentAnonymousData.json");
            FileWriter unsentDataFile = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(unsentDataFile);
            if (!dataOlderThanOneMonth(jsonString) && file.length() < 10000) {
                bw.write(jsonString);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
