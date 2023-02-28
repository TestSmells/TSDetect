package org.testsmells.server.controller.requests;


import org.jooq.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.testsmells.server.repository.DBInputTool;
import org.testsmells.server.service.TestResultService;

@RestController
public class TestResultController {

    @Autowired
    TestResultService testResultService;

    /*
        Method for taking the incoming JSON strong from the plugin, deserializing it, verifying the timestamp and uuid
        are correct, then passing the body to the database API, returning a 200 response in the event of success
        Any other HTTP should be considered an error
     */

    @PostMapping(value = "/test-results", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> testResults(@RequestBody String json){
        System.out.println(json);
        boolean result = testResultService.sendSmells(json);
        if(!result){
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.ok().build();
        }
    }


}
