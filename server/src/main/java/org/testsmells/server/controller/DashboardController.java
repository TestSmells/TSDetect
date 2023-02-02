package org.testsmells.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.testsmells.server.service.DashboardService;

import java.util.HashMap;

@RestController
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

//    @RequestMapping(path = "/test-smells", method = RequestMethod.GET)
//    public ResponseEntity<List<TestSmells>> getTestSmells() {
//        return ResponseEntity.ok(exampleService.getTestSmells());
//    }

    @GetMapping("/test-smells")
    public ResponseEntity<HashMap<String, Long>> getTestSmells() {

        HashMap<String, Long> smellsAndCounts = dashboardService.getTestSmells();

        return new ResponseEntity(smellsAndCounts, HttpStatus.OK);
    }

}
