package org.testsmells.server.controller.requests;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

public class TestResults {
    @NotNull
    private String userID;
    @NotNull
    private String timestamp;
    @NotNull
    private Map<String, Integer> smellCount;

    public TestResults(String userID, String timestamp, Map<String, Integer> smellCount) {
        this.userID = userID;
        this.timestamp = timestamp;
        this.smellCount = smellCount;
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

    @PostMapping(value = "/test-results", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> testResults(
            @RequestBody String uid, String ts, Map<String, Integer> sc) throws Exception{
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
