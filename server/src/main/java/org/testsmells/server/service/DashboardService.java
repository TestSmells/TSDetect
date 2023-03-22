package org.testsmells.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testsmells.server.repository.DBOutputTool;
import java.sql.Timestamp;
import java.util.*;

@Service
public class DashboardService {

    @Autowired
    DBOutputTool outputRepository;

    /**
     * Retrieves test smells from the database dependent on the provided parameters
     *
     * @param dateTime an optional parameter that may contain a date time to restrict test smell search
     * @param smellTypes an optional parameter that may contain a list of smell types to filter in the test smell search
     * @return a hashmap of tests smells keyed to their quantities
     */
    public HashMap<String, Long> getTestSmells(Optional<Integer> dateTime, Optional<List> smellTypes) {
        // SQL exception is handled in the DBOutputTool
        if (dateTime.isPresent() && smellTypes.isPresent()) {
            return outputRepository.outTestSmellData(getTimeStamp(dateTime.get()), parseSmellList(smellTypes.get()));
        } else if (dateTime.isPresent()) {
            return outputRepository.outTestSmellData(getTimeStamp(dateTime.get()));
        } else if (smellTypes.isPresent()) {
            return outputRepository.outTestSmellData(parseSmellList(smellTypes.get()));
        } else {
            return outputRepository.outTestSmellData();
        }
    }

    /**
     * Private helper to determine if the given object is a list, and if so, collect all String values from that list
     * @param smells an Object to determine its status as a list and collect String data from
     * @return an ArrayList of Strings containing all String data from the given object
     */
    private ArrayList<String> parseSmellList(Object smells){
        ArrayList<String> smellList = new ArrayList<>();

        if(smells instanceof List)
            for(Object smell : (List)smells)
                if(smell instanceof String)
                    smellList.add(smell.toString());

        return smellList;
    }

    /**
     * private helper function that receives an object, determines if it is a number, and returns a timestamp that is equivalent
     * to a date "dateTime" number of days before the current date.
     * @param dateTime an object to determine its status as a number, and use to find the desired timestamp
     * @return a timestamp equal to the date "dateTime" number of days before the current server datetime
     */
    private Timestamp getTimeStamp(Object dateTime){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("Now: " + timestamp);

        int numDaysCovered = 0;

        // if datetime is an integer, use that integer
        if (dateTime instanceof Integer) {
            numDaysCovered = (Integer) dateTime;
        } else if (dateTime instanceof String) { // if datetime is a string, see if it's a string of an integer, and if so, use it
            try {
                numDaysCovered = Integer.parseInt((String)dateTime);
            } catch(NumberFormatException e) {
                System.out.println("dateTime was not an integer");
            }
        } else {
            System.out.println("DateTime must be an integer or a String, not a " + dateTime.getClass());
        }

        // find the day "numDaysCovered" in the past
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);
        calendar.add(Calendar.DATE, (numDaysCovered * -1)); //invert so the number of days is subtracted
        timestamp.setTime(calendar.getTime().getTime());

        return timestamp;
    }

}
