package org.scanl.plugins.tsdetect.model;

import net.minidev.json.JSONObject;
import org.scanl.plugins.tsdetect.config.application.AppSettingsState;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Objects;

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
                URL url = new URL("http://localhost:8080");
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Connection", "close");

                postRequest(conn, 1);
            } catch (Exception e) {
                System.out.println(e);
            }
        //}

    }

    public void postRequest(URLConnection conn, int tryNum) throws IOException {
        //do not attempt to send the data more than 5 times
        if (tryNum > 5) {
            return;
        }

        JSONObject jsonWrap = new JSONObject(this.getHashMap());
        try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
            dos.writeBytes(jsonWrap.toString());
        }

        //placeholder retry logic for now
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = bf.readLine()) != null) {
                System.out.println(line);
                if (line.contains("statusCode") && !line.contains("200")) {
                    postRequest(conn, tryNum++);
                }
            }
        }
    }
}
