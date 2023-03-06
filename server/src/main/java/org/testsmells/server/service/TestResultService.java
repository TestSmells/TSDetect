package org.testsmells.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testsmells.server.repository.DBInputTool;
import org.testsmells.server.repository.ExampleRepository;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static java.lang.Integer.parseInt;

@Service
public class TestResultService {

    @Autowired
    DBInputTool dbInputTool;

    public boolean sendSmells(String json) {
        HashMap<String,String> map = parseJson(json);
        HashMap<String, Integer> smells = new HashMap<String, Integer>();

        String uuid = null;
        String ts = null;

        if(map.containsKey("uuid")){
            uuid = map.get("uuid");
            map.remove("uuid");
        }
        if(map.containsKey("timestamp")) {
            ts = map.get("timestamp");
            map.remove("timestamp");
        }

        //Verifies the uuid and timestamp match the enforced format
        if(!(isValidUuid(uuid) && isValidDate(ts))){
            //return bad response
            System.out.println("Bad uuid or timestamp");
            System.out.println(uuid + ": " + isValidUuid(uuid));
            System.out.println(ts + ": " + isValidDate(ts));
            return false;
        }

        map.forEach((k,v) -> smells.put(k, parseInt(v)));

        //converts the timestamp from string to Timestamp
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(ts);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            //pass values to database input tool
            HashMap<String, Integer> results =  dbInputTool.inputData(uuid, timestamp, smells);

            if (!results.isEmpty() && results.containsKey(uuid) && results.get(uuid) != 0){
                return true;
            }else{
                return false;
            }
        } catch (ParseException e) {
            return false;
        }

    }
    //makes sure hashmap in ER is populated, otherwise failure
    public boolean isValidUuid(String uuid){
        return uuid != null && uuid.length() == 36;
    }

    public boolean isValidDate(String date){

        if (date == null) {
            return false;
        }

        //example correct timestamp is "2023-02-07 18:26:50.952"
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sd.setLenient(false);
        try {
            sd.parse(date.trim());
        } catch (ParseException pe) {
            System.out.println(pe);
            return false;
        }
        return true;
    }

    private HashMap<String,String> parseJson(String json){

        HashMap<String, String> result = new HashMap<>();
        String[] pairs = json.split(",");

        for(String pair : pairs) {
            String[] keyValue = pair.split(":", 2);
            if(keyValue.length == 2){
                result.put(keyValue[0].trim().replaceAll("\"", ""), keyValue[1].trim().replaceAll("\"", ""));
            }
        }
        return result;

    }
}
