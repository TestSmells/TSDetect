package org.testsmells.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testsmells.server.repository.DBInputTool;

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

    /**
     * Method that recevies an incoming JSON string from the plugin, verifies the input,
     * maps that input to a HashMap, then passes it to the DBInputTool
     * @param json: Incoming JSON string received from a successful plugin run
     *        Example: "uuid" : " VALID_UUID ", "timestamp" : " VALID_TIMESTAMP_STRING ", "Assertion Roulette": "1"
     * @return: boolean to indicate the connection was successful
     */
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

    /**
     * Helper method to verify the incoming UUID is of the correct format, a 36 character alpphanumeric string
     * @param uuid: String containing incoming UUID
     * @return: boolean value indicating whether the uuid is valid
     */
    public boolean isValidUuid(String uuid){
        return uuid != null && uuid.length() == 36;
    }

    /**
     * Helper method to verify the incoming date value is of the correct format
     * Example of valid date format is "2023-02-07 18:26:50.952"
     * @param date: String containing incoming data value
     * @return: boolean value indicating whether the date is valid
     */
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

    /**
     * Helper method to parse the incoming JSON into a format digestable by the database
     * @param json: Incoming JSON string to be unwrapped
     * @return: Hashmap containing <String, String> key value pairs including the key: uuid value: (example valid UUID),
     * key: timestamp value: (2023-02-07 18:26:50.952), and applicable smell types with key: name value: count
     * types
     */
    private HashMap<String,String> parseJson(String json){

        String cleanString = json.replaceAll("}", "").replaceAll("[{]","");

        HashMap<String, String> result = new HashMap<>();
        String[] pairs = cleanString.split(",");

        for(String pair : pairs) {
            String[] keyValue = pair.split(":", 2);
            if(keyValue.length == 2){
                result.put(keyValue[0].trim().replaceAll("\"", ""), keyValue[1].trim().replaceAll("\"", ""));
            }
        }
        return result;

    }
}
