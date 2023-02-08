package org.testsmells.server.controller.requests;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Map;

public class TestResults {

    /*
        Method for taking the incoming JSON strong from the plugin, deserializing it, verifying the timestamp and uuid
        are correct, then passing the body to the database API, returning a 200 response in the event of success
        Any other HTTP should be considered an error
     */
    @PostMapping(value = "/test-results", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> testResults(@RequestBody String json) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, String>> typeRef = new TypeReference<>() {};
        Map<String, String> map = mapper.readValue(json, typeRef);

        String uuid = map.get("uuid");
        String ts = map.get("timestamp");

        //Verifies the uuid and timestamp match the enforced format
        if(isValidUuid(uuid) && isValidDate(ts)){

        }

        return ResponseEntity.ok().build();
    }
    public boolean isValidUuid(String uuid){
        //TODO: fill this out when UID generation is implemented
        return true;
    }
    public boolean isValidDate(String date){
        //example correct timestamp is "2023-02-07 18:26:50.952"
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        sd.setLenient(false);
        try {
            sd.parse(date.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

}
