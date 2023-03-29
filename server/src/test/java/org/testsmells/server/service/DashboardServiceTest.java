package org.testsmells.server.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.testsmells.server.repository.DBOutputTool;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DashboardServiceTest {

    @MockBean
    private DBOutputTool dbOutputTool;

    @Autowired
    private DashboardService dashboardService;

    // Constant data for mocked call returns
    private final HashMap<String, Long> smellsEmpty = new HashMap<>();

    @Test
    public void getTestSmellsAllSuccess() {
        // Given
        Map.Entry<String, Long> test1 = Map.entry("TestName1", 12L);
        Map.Entry<String, Long> test2 = Map.entry("TestName2", 432L);
        Map.Entry<String, Long> test3 = Map.entry("TestName3", 6123151241L);
        HashMap<String, Long> expectedTestSmells = new HashMap<>(){};
        expectedTestSmells.put(test1.getKey(), test1.getValue());
        expectedTestSmells.put(test2.getKey(), test2.getValue());
        expectedTestSmells.put(test3.getKey(), test3.getValue());

        when(dbOutputTool.outTestSmellData()).thenReturn(expectedTestSmells);

        // When
        HashMap<String, Long> testSmells = dashboardService.getTestSmells(Optional.empty(), Optional.empty());

        // Then
        assertEquals(3, testSmells.size());
        assertEquals(expectedTestSmells, testSmells);
    }

    @Test
    public void getTestSmellsByTimestampSuccess() {
        // Given
        Map.Entry<String, Long> test1 = Map.entry("TestName1", 55443322L);
        Map.Entry<String, Long> test2 = Map.entry("TestName2", 711233L);
        HashMap<String, Long> expectedTestSmells = new HashMap<>(){};
        expectedTestSmells.put(test1.getKey(), test1.getValue());
        expectedTestSmells.put(test2.getKey(), test2.getValue());

        Integer days = 7;
        when(dbOutputTool.outTestSmellData(any(Timestamp.class))).thenReturn(expectedTestSmells);

        // When
        HashMap<String, Long> testSmells = dashboardService.getTestSmells(Optional.of(days), Optional.empty());

        // Then
        assertEquals(2, testSmells.size());
        assertEquals(expectedTestSmells, testSmells);
    }

    @Test
    public void getTestSmellsBySmellTypesSuccess() {
        // Given
        Map.Entry<String, Long> test1 = Map.entry("TestName1", 325678324L);
        Map.Entry<String, Long> test2 = Map.entry("TestName2", 7L);
        HashMap<String, Long> expectedTestSmells = new HashMap<>(){};
        expectedTestSmells.put(test1.getKey(), test1.getValue());
        expectedTestSmells.put(test2.getKey(), test2.getValue());

        ArrayList<String> smellTypes = new ArrayList<>();
        smellTypes.add("TestName1");
        smellTypes.add("TestName2");

        when(dbOutputTool.outTestSmellData(smellTypes)).thenReturn(expectedTestSmells);

        // When
        HashMap<String, Long> testSmells = dashboardService.getTestSmells(Optional.empty(), Optional.of(smellTypes));

        // Then
        assertEquals(2, testSmells.size());
        assertEquals(expectedTestSmells, testSmells);
    }

    @Test
    public void getTestSmellsBySmellTypesAndTimestampSuccess() {
        // Given
        Map.Entry<String, Long> test1 = Map.entry("TestName1", 123123L);
        Map.Entry<String, Long> test2 = Map.entry("TestName2", 522L);
        Map.Entry<String, Long> test3 = Map.entry("TestName3", 12L);
        Map.Entry<String, Long> test4 = Map.entry("TestName4", 3224L);
        HashMap<String, Long> expectedTestSmells = new HashMap<>(){};
        expectedTestSmells.put(test1.getKey(), test1.getValue());
        expectedTestSmells.put(test2.getKey(), test2.getValue());
        expectedTestSmells.put(test3.getKey(), test3.getValue());
        expectedTestSmells.put(test4.getKey(), test4.getValue());

        ArrayList<String> smellTypes = new ArrayList<>();
        smellTypes.add("TestName1");
        smellTypes.add("TestName2");
        smellTypes.add("TestName3");
        smellTypes.add("TestName4");

        Integer days = 30;
        when(dbOutputTool.outTestSmellData(any(Timestamp.class), eq(smellTypes))).thenReturn(expectedTestSmells);

        // When
        HashMap<String, Long> testSmells = dashboardService.getTestSmells(Optional.of(days), Optional.of(smellTypes));

        // Then
        assertEquals(4, testSmells.size());
        assertEquals(expectedTestSmells, testSmells);
    }

    @Test
    public void getTestSmellsBySmellTypesAndZeroDateSuccess() {
        // Given
        ArrayList<String> smellTypes = new ArrayList<>();
        smellTypes.add("TestName1");
        smellTypes.add("TestName2");

        HashMap<String, Long> expectedTestSmells = new HashMap<>();
        when(dbOutputTool.outTestSmellData(any(Timestamp.class), any())).thenReturn(expectedTestSmells);

        // When
        HashMap<String, Long> testSmells = dashboardService.getTestSmells(Optional.of(0), Optional.of(smellTypes));

        // Then
        assertEquals(0, testSmells.size());
        assertEquals(expectedTestSmells, testSmells);
    }

    @Test
    public void getTestSmellsBySmellTypesAndNegativeDateSuccess() {
        // Given
        ArrayList<String> smellTypes = new ArrayList<>();
        smellTypes.add("TestName1");
        smellTypes.add("TestName2");

        HashMap<String, Long> expectedTestSmells = new HashMap<>();
        when(dbOutputTool.outTestSmellData(any(Timestamp.class), any())).thenReturn(expectedTestSmells);

        // When
        HashMap<String, Long> testSmells = dashboardService.getTestSmells(Optional.of(-123123), Optional.of(smellTypes));

        // Then
        assertEquals(0, testSmells.size());
    }

    @Test
    public void getTestSmellsWithEmptySmellTypesAndValidDateSuccess() {
        // Given
        Map.Entry<String, Long> test1 = Map.entry("TestName1", 123123L);
        Map.Entry<String, Long> test4 = Map.entry("TestName4", 3224L);
        HashMap<String, Long> expectedTestSmells = new HashMap<>(){};
        expectedTestSmells.put(test1.getKey(), test1.getValue());
        expectedTestSmells.put(test4.getKey(), test4.getValue());

        when(dbOutputTool.outTestSmellData(any(Timestamp.class), any())).thenReturn(expectedTestSmells);

        // When
        HashMap<String, Long> testSmells = dashboardService.getTestSmells(Optional.of(400), Optional.of(new ArrayList<>()));

        // Then
        assertEquals(2, testSmells.size());
        assertEquals(expectedTestSmells, testSmells);
    }

    /*
        I would like to see tests for a datetime value of 0 and a negative value, as well as a test with a Smell list that is empty.

        We could also probably do a null test for the datetime and smell list, that on. That one is more debatable is it should technically be impossible for this to receive a null value.
     */

}
