package org.testsmells.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.testsmells.server.service.DashboardService;

import javax.xml.bind.SchemaOutputResolver;
import java.sql.Timestamp;
import java.util.*;

@RestController
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

//    @RequestMapping(path = "/test-smells", method = RequestMethod.GET)
//    public ResponseEntity<List<TestSmells>> getTestSmells() {
//        return ResponseEntity.ok(exampleService.getTestSmells());
//    }

    /**
     * The main endpoint for accessing data from the TSDetect database.
     * @param dateTime The number of days into the past from the current date to gather data from. If excluded, will gather
     *                 data from all dates. Optional
     * @param smellTypes A list of test smell types to search the database for information for. If excluded, will gather
     *                   information for all available test smells. Optional.
     * @return A response entity containing a hashmap of test smell names keyed to the quantity of that smell to appear
     *          within the queried dates.
     */
    @GetMapping("/test-smells")
    public ResponseEntity<HashMap<String, Long>> getTestSmells(@RequestParam("datetime") Optional<Integer> dateTime,@RequestParam("smell_type") Optional<List> smellTypes) {

        HashMap<String, Long> smellsAndCounts = new HashMap<>();

        //grab everything if no parameters are given
        if(!dateTime.isPresent() && !smellTypes.isPresent()) {
            System.out.println("Grabbing all test smell data available");
            smellsAndCounts = dashboardService.getTestSmells();

        }
        //grab data for specified smells over all time
        else if(!dateTime.isPresent() && smellTypes.isPresent() ) {
            ArrayList<String> smells = parseSmellList(smellTypes.get());
            //smellsAndCounts = dashboardService.getTestSmells(smells);

        }
        //Grab all test smell data in the previous specified number of days
        else if(dateTime.isPresent() && !smellTypes.isPresent() ) {
            Timestamp timestamp = getTimeStamp(dateTime.get());
            smellsAndCounts = dashboardService.getTestSmells(timestamp);

        }
        //Grab data for specified smells over the previous specified number of days
        else{
            Timestamp timestamp = getTimeStamp(dateTime.get());
            ArrayList<String> smells = parseSmellList(smellTypes.get());
            smellsAndCounts = dashboardService.getTestSmells(timestamp, smells);

        }
        if(smellsAndCounts.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity(smellsAndCounts, HttpStatus.OK);
        }
    }

    /**
     * Private helper to determine if the given object is a list, and if so, collect all String values from that list
     * @param smells an Object to determine its status as a list and collect String data from
     * @return an ArrayList of Strings containing all String data from the given object
     */
    private ArrayList<String> parseSmellList(Object smells){

        ArrayList<String> smellList = new ArrayList<String>();

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

        //if datetime is an integer, use that integer
        if(dateTime instanceof Integer){
            numDaysCovered = (Integer) dateTime;
        }else if(dateTime instanceof String){ //if datetime is a string, see if it's a string of an integer, and if so, use it
            try{
                numDaysCovered = Integer.parseInt((String)dateTime);
            }catch(NumberFormatException e){
                System.out.println("dateTime was not an integer");
            }
        }else{
            System.out.println("DateTime must be an integer or a String, not a " + dateTime.getClass());
        }

        //find the day "numDaysCovered" in the past
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);
        calendar.add(Calendar.DATE, (numDaysCovered * -1)); //invert so the number of days is subtracted
        timestamp.setTime(calendar.getTime().getTime());

        return timestamp;

    }

}
