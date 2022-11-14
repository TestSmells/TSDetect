package org.scanl.plugins.tsdetect.model;

import net.minidev.json.JSONObject;

import java.util.HashMap;

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
        JSONObject jsonWrap = new JSONObject(this.getHashMap());
        System.out.println(jsonWrap);
    }
}
