package org.scanl.plugins.tsdetect.model;

import com.intellij.openapi.project.ProjectManager;
import net.minidev.json.JSONObject;
import org.apache.commons.httpclient.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.*;
import java.util.*;

public class AnonymousData {
    private HashMap<String, String> data;

    public AnonymousData() {
        data = new HashMap<>();
    }

    public HashMap<String, String> getHashMap() {
        return data;
    }

    public void addData(String key, String value) {
        data.put(key, value);
    }

    public void sendData() {
        //only attempt to send the data if the user has opted in
        //TODO - uncomment following lines after pop up has been merged
        //AppSettingsState settings = new AppSettingsState();
        //if (Objects.requireNonNull(settings.getState()).settings.get("OPT-IN")) {
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
                JSONObject jsonWrap = new JSONObject(this.getHashMap());
                postRequest(jsonWrap.toJSONString(), 1);
            } catch (Exception e) {
                System.out.println(e);
            }
        //}
    }

    public void postRequest(String jsonString, int tryNum) throws IOException {
        //do not attempt to send the data more than 5 times
        //save the data locally
        if (tryNum > 5) {
            localSave(jsonString);
        } else {
            try {
                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpPost post = new HttpPost("http://localhost:8080");
                post.setHeader("Content-Type", "application/json");
                post.setHeader("Connection", "close");
                BasicHttpEntity httpEntity = new BasicHttpEntity();
                httpEntity.setContent(new ByteArrayInputStream(jsonString.getBytes()));
                post.setEntity(httpEntity);

                HttpResponse httpResponse = httpClient.execute(post);
                if (!httpResponse.getStatusLine().toString().contains("200")) {
                    System.out.println("FAIL");
                    postRequest(jsonString, tryNum+1);
                } else {
                    System.out.println("SUCCESS");
                }
            } catch (HttpException e) {
                System.out.println(e);
            }
        }
    }

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
