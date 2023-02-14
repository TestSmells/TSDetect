package org.testsmells.server.controller.requests;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.testsmells.server.service.TestResultService;


public class TestResultController {

    /*
        Method for taking the incoming JSON strong from the plugin, deserializing it, verifying the timestamp and uuid
        are correct, then passing the body to the database API, returning a 200 response in the event of success
        Any other HTTP should be considered an error
     */
    @PostMapping(value = "/test-results", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> testResults(@RequestBody String json) throws Exception{
        boolean result = TestResultService.sendSmells(json);
        if(!result){
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.ok().build();
        }
    }


}
