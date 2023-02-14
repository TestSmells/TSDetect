package org.testsmells.server.service;

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
import java.util.Map;

import static java.lang.Integer.parseInt;

@Service
public class TestResultService {
    @Autowired
    static
    DBInputTool dbInputTool;

    public static boolean sendSmells(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, String>> typeRef = new TypeReference<>() {};
        Map<String, String> map = mapper.readValue(json, typeRef);
        //HashMap<String, String> mappedJson = new HashMap<String, String>();
        HashMap<String, Integer> smells = new HashMap<String, Integer>();

        String uuid = map.get("uuid");
        map.remove("uuid");
        String ts = map.get("timestamp");
        map.remove("timestamp");

        //Verifies the uuid and timestamp match the enforced format
        if(!(isValidUuid(uuid) && isValidDate(ts))){
            //return bad response
            return false;
        }

        map.forEach((k,v) -> smells.put(k, parseInt(v)));

        //converts the timestamp from string to Timestamp
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Date parsedDate = dateFormat.parse(ts);
        Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

        //pass values to database input tool
        dbInputTool.inputData(uuid, timestamp, smells);
        return true;

    }
    //makes sure hashmap in ER is populated, otherwise failure
    public static boolean isValidUuid(String uuid){
        return uuid.length() == 36;
    }
    public static boolean isValidDate(String date){
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
