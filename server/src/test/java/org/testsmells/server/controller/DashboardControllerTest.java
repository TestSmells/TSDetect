package org.testsmells.server.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testsmells.server.service.DashboardService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DashboardControllerTest {

    @Autowired
    DashboardController dashboardController;

    @MockBean
    DashboardService dashboardService;

    @Test
    public void getTestSmellsSuccess() {
        // Given
        Integer days = 12;
        ArrayList<String> smellList = new ArrayList<>();
        smellList.add("RandomTestName");
        smellList.add("TestNameTWO");

        Optional<Integer> dayOptional = Optional.of(days);
        Optional<List> smellListOptional = Optional.of(smellList);

        HashMap<String, Long> responseMap = new HashMap<>();
        responseMap.put("RandomTestName", 10L);
        responseMap.put("TestNameTWO", 52333L);

        when(dashboardService.getTestSmells(eq(dayOptional), eq(smellListOptional))).thenReturn(responseMap);

        // When
        ResponseEntity<HashMap<String, Long>> response =
                dashboardController.getTestSmells(dayOptional, smellListOptional);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseMap, response.getBody());
    }

    @Test
    public void getTestSmellsWithDateParamSuccess() {
        // Given
        HashMap<String, Long> responseMap = new HashMap<>();
        responseMap.put("smell1", 212121L);
        responseMap.put("Smell-two-yup", 2L);

        Optional<Integer> day = Optional.of(13);
        when(dashboardService.getTestSmells(eq(day), any())).thenReturn(responseMap);

        // When
        ResponseEntity<HashMap<String, Long>> response =
                dashboardController.getTestSmells(day, Optional.empty());

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseMap, response.getBody());
    }

    @Test
    public void getTestSmellsWithSmellTypeParamSuccess() {
        // Given
        HashMap<String, Long> responseMap = new HashMap<>();
        responseMap.put("hello", 10000000L);
        responseMap.put("aTestNameMaybe?", 63L);

        ArrayList<String> smellList = new ArrayList<>();
        smellList.add("hello");
        smellList.add("aTestNameMaybe?");
        Optional<List> smellListOptional = Optional.of(smellList);

        Optional<Integer> day = Optional.of(13);
        when(dashboardService.getTestSmells(any(), eq(smellListOptional))).thenReturn(responseMap);

        // When
        ResponseEntity<HashMap<String, Long>> response =
                dashboardController.getTestSmells(Optional.empty(), smellListOptional);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseMap, response.getBody());
    }

    @Test
    public void getTestSmellsFailure() {
        // Given
        when(dashboardService.getTestSmells(any(), any())).thenReturn(new HashMap<>());

        // When
        ResponseEntity<HashMap<String, Long>> response =
                dashboardController.getTestSmells(Optional.empty(), Optional.empty());

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void getTestSmellsWithDateParamFailure() {
        // Given
        Optional<Integer> day = Optional.of(99);
        when(dashboardService.getTestSmells(eq(day), any())).thenReturn(new HashMap<>());

        // When
        ResponseEntity<HashMap<String, Long>> response =
                dashboardController.getTestSmells(day, Optional.empty());

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void getTestSmellsWithSmellTypeParamFailure() {
        // Given
        ArrayList<String> smellList = new ArrayList<>();
        smellList.add("SmellNAMEEE");
        smellList.add("SMELL_TWO");

        Optional<List> smellListOptional = Optional.of(smellList);

        when(dashboardService.getTestSmells(any(), eq(smellListOptional))).thenReturn(new HashMap<>());

        // When
        ResponseEntity<HashMap<String, Long>> response =
                dashboardController.getTestSmells(Optional.empty(), smellListOptional);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
