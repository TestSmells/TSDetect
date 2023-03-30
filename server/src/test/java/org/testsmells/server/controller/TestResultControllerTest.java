package org.testsmells.server.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testsmells.server.controller.requests.TestResultController;
import org.testsmells.server.service.TestResultService;
import org.testsmells.server.repository.Constants;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


//of note: the controller does no data checking, so these tests are simply testing that the controller properly allows the
//service to determine which response code is sent back to the user
@SpringBootTest
public class TestResultControllerTest {

    @Autowired
    private TestResultController testResultController;

    @MockBean
    private TestResultService testResultService;

    private HashMap<String, String> insert;
    private final String USERNAME_STRING = "uuid";
    private final String TIMESTAMP_STRING = "timestamp";

    @BeforeEach
    public void init(){
        insert = new HashMap<>();
    }

    @Test
    public void testInsertOneTestValid(){
        insert.put(USERNAME_STRING, "abcd-efgh-ijkl-mnop-qrst-uvwx-yzab");
        insert.put(TIMESTAMP_STRING, "2023-02-27 19:26:16.408");
        insert.put(Constants.ASSERTION_ROULETTE, "1");

        ResponseEntity expectedResponse = new ResponseEntity<>(HttpStatus.OK);

        when(testResultService.sendSmells(insert.toString())).thenReturn(true);
        assertEquals(testResultController.testResults(insert.toString()), expectedResponse);


    }

    @Test
    public void testInsertOneTestinvalid(){
        insert.put(USERNAME_STRING, "abcd-efgh-ijkl-mnop-qrst-uvwx-yzab");
        insert.put(TIMESTAMP_STRING, "2023-02-27 19:26:16.408");
        insert.put("I'mInvalidNeenerneener", ":P");

        ResponseEntity expectedResponse = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        when(testResultService.sendSmells(insert.toString())).thenReturn(false);
        assertEquals(testResultController.testResults(insert.toString()), expectedResponse);



    }

}
