package org.testsmells.server.controller.requests;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

public class TestResults {
    @NotNull
    private String userID;
    private String timestamp;
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

//    @PostMapping(value = "/test-results", consumes = "application/json", produces = "application/json")
//    public ResponseEntity<Object> testResults(
//            @RequestHeader(name = userID, required = true) String uuid,
//            @RequestHeader(value = timestamp, required = true) String ts,
//            @RequestHeader(value = smellCount, required = true) Map<String, Integer> s) throws Exception{
//
//    }

}
