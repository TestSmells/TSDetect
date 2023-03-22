package org.scanl.plugins.tsdetect.model;

import com.intellij.openapi.project.ProjectManager;
import net.minidev.json.JSONObject;
import org.apache.commons.httpclient.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.scanl.plugins.tsdetect.common.PluginResourceBundle;
import org.scanl.plugins.tsdetect.config.PluginSettings;

import java.io.*;
import java.sql.Timestamp;
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
     * The primary function for gathering and sending data to the server. If unsent data
     * exists in a JSON file in the project directory, store that data and delete the file.
     * Attempt to send unsent data followed by current data. If any data is unable to be
     * sent to the server, store it in a new JSON file.
     */
    public void sendData() {
        //only attempt to send the data if the user has opted in
        if (PluginSettings.GetSetting("OPT_IN")) {
            System.out.println("Sending data...");
            addData("uuid", PluginSettings.uuid());
            addData("timestamp", new Timestamp(System.currentTimeMillis()).toString());
            try {
                //get unsent data file
                File dataFile = new File(ProjectManager.getInstance().getOpenProjects()[0].getBasePath() + "/unsentAnonymousData.json");
                if (dataFile.exists()) {
                    Scanner scanner = new Scanner(dataFile);

                    //if file is not empty, store each line
                    List<String> oldData = new ArrayList<>();
                    while(scanner.hasNextLine()) {
                        oldData.add(scanner.nextLine());
                    }

                    //delete old data file
                    dataFile.delete();

                    //send old data
                    for (String jsonString : oldData) {
                        postRequest(jsonString, 1);
                    }
                }
                //send current data
                JSONObject jsonWrap = new JSONObject(this.data);
                postRequest(jsonWrap.toJSONString(), 1);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    /**
     * HTTP POST request to the server. Attempt to send the data for a maximum of
     * 5 tries. A 200 status code in the response indicates data was successfully
     * received by the server.
     * @param jsonString - String of JSON data to be sent. Sending old data requires
     *                     this parameter to be a string.
     * @param tryNum - the current attempt
     * @throws IOException - catches IO exceptions
     */
    public void postRequest(String jsonString, int tryNum) throws IOException {
        if (tryNum > 5) {
            localSave(jsonString);
        } else {
            try {
                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpPost post = new HttpPost(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "PLUGIN.DATA.HOSTNAME"));
                post.setHeader("Content-Type", "application/json");
                post.setHeader("Connection", "close");
                BasicHttpEntity httpEntity = new BasicHttpEntity();
                httpEntity.setContent(new ByteArrayInputStream(jsonString.getBytes()));
                post.setEntity(httpEntity);

                HttpResponse httpResponse = httpClient.execute(post);
                if (!httpResponse.getStatusLine().toString().contains("200")) {
                    //TODO remove before merge
                    System.out.println("FAIL " + httpResponse.getStatusLine().toString());
                    //attempt to send the data again for a maximum of 5 tries
                    postRequest(jsonString, tryNum+1);
                } else {
                    //TODO remove before merge
                    System.out.println("SUCCESS");
                }
            } catch (HttpException e) {
                e.printStackTrace();
                localSave(jsonString);
            }
        }
    }

    /**
     * Saves data locally in a JSON file located in the base of the project directory.
     * @param jsonString the string of JSON data to be saved
     */
    public void localSave(String jsonString) {
        //saves data that was unable to be sent after 5 tries
        try {
            FileWriter unsentDataFile = new FileWriter(new File(ProjectManager.getInstance().getOpenProjects()[0].getBasePath(), "unsentAnonymousData.json"), true);
            BufferedWriter bw = new BufferedWriter(unsentDataFile);
            bw.write(jsonString);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}