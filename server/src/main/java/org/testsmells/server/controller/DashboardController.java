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
    public ResponseEntity<HashMap<String, Long>> getTestSmells(@RequestParam("datetime") Optional<Integer> dateTime,
                                                               @RequestParam("smell_type") Optional<List> smellTypes) {
        HashMap<String, Long> smellsAndCounts = dashboardService.getTestSmells(dateTime, smellTypes);
        if (smellsAndCounts.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(smellsAndCounts);
        }
    }

}
