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

    @GetMapping("/test-smells")
    public ResponseEntity<HashMap<String, Long>> getTestSmells(@RequestParam("datetime") Optional<Integer> dateTime,@RequestParam("smell_type") Optional<List> smellTypes) {

        HashMap<String, Long> smellsAndCounts = new HashMap<>();

        if(!dateTime.isPresent() && !smellTypes.isPresent()) {
            System.out.println("Grabbing all test smell data available");
            smellsAndCounts = dashboardService.getTestSmells();
        }
        else if(!dateTime.isPresent() && smellTypes.isPresent() ) {
            //TODO: We don't actually have functionallity for this
        }else if(dateTime.isPresent() && !smellTypes.isPresent() ) {
            Timestamp timestamp = getTimeStamp(dateTime.get());
            smellsAndCounts = dashboardService.getTestSmells(timestamp);
        }else{
            Timestamp timestamp = getTimeStamp(dateTime.get());
            ArrayList<String> smells = parseSmellList(smellTypes.get());
            smellsAndCounts = dashboardService.getTestSmells(timestamp, smells);
        }
        return new ResponseEntity(smellsAndCounts, HttpStatus.OK);
    }

    private ArrayList<String> parseSmellList(Object smells){

        ArrayList<String> smellList = new ArrayList<String>();

        if(smells instanceof List)
            for(Object smell : (List)smells)
                if(smell instanceof String)
                    smellList.add(smell.toString());

        return smellList;

    }

    private Timestamp getTimeStamp(Object dateTime){

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("Now: " + timestamp);

        if(dateTime instanceof Integer){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(timestamp);
            calendar.add(Calendar.DATE, ((Integer)dateTime * -1));
            timestamp.setTime(calendar.getTime().getTime());
            System.out.println("New Time: " + timestamp);
        }else{
            System.out.println(dateTime.getClass());
        }

        return timestamp;

    }

}
