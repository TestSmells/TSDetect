package org.testsmells.server.controller.requests;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestResults {
    @NotNull
    private String userID;
    @NotNull
    private String timestamp;
    @NotNull
    private Map<String, Integer> smellCount;


    private ArrayList<String> vars;

    public TestResults(String userID, String timestamp, Map<String, Integer> smellCount, ArrayList<String> vars) {
        this.userID = userID;
        this.timestamp = timestamp;
        this.smellCount = smellCount;
        this.vars = vars;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Integer> getSmellCount() {
        return smellCount;
    }

    public void setSmellCount(Map<String, Integer> smellCount) {
        this.smellCount = smellCount;
    }

    public ArrayList<String> getVars() {
        return vars;
    }

    public void setVars(ArrayList<String> vars) {
        this.vars = vars;
    }

    @PostMapping(value = "/test-results", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> testResults(@RequestBody String json) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Integer>> typeRef = new TypeReference<HashMap<String, Integer>>() {};
        Map<String, Integer> map = mapper.readValue(json, typeRef);
        for(String key: map.keySet()){
            vars.add(key);
        }
        String uid = vars.get(0);
        String ts = vars.get(1);

        if(isValidUid(uid) && isValidDate(ts)){

        }

        return ResponseEntity.ok().build();
    }
    public boolean isValidUid(String uid){
        //TODO: fill this out when UID generation is implemented
        return true;
    }
    public boolean isValidDate(String date){
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        sd.setLenient(false);
        try {
            sd.parse(date.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

}
