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
import static org.mockito.Mockito.when;

@SpringBootTest
public class DashboardServiceTest {

    @MockBean
    DBOutputTool dbOutputTool;

    @Autowired
    DashboardService dashboardService;

    // Constant data for mocked call returns
    private final HashMap<String, Long> smellsEmpty = new HashMap<>();

    @Test
    public void getTestSmellsAllSuccess() {
        // Given
        Map.Entry<String, Long> test1 = Map.entry("TestName1", 12L);
        Map.Entry<String, Long> test2 = Map.entry("TestName2", 432L);
        Map.Entry<String, Long> test3 = Map.entry("TestName3", 6123151241L);
        HashMap<String, Long> expectedResponse = new HashMap<>(){};
        expectedResponse.put(test1.getKey(), test1.getValue());
        expectedResponse.put(test2.getKey(), test2.getValue());
        expectedResponse.put(test3.getKey(), test3.getValue());

        when(dbOutputTool.outTestSmellData()).thenReturn(expectedResponse);

        // When
        HashMap<String, Long> testSmells = dashboardService.getTestSmells(Optional.empty(), Optional.empty());

        // Then
        assertEquals(3, testSmells.size());
        assertTrue(testSmells.containsKey(test1.getKey()));
        assertTrue(testSmells.containsKey(test2.getKey()));
        assertTrue(testSmells.containsKey(test3.getKey()));
        assertEquals(test1.getValue(), testSmells.get(test1.getKey()));
        assertEquals(test2.getValue(), testSmells.get(test2.getKey()));
        assertEquals(test3.getValue(), testSmells.get(test3.getKey()));
    }

    @Test
    public void getTestSmellsByTimestampSuccess() {
        // Given
        Map.Entry<String, Long> test1 = Map.entry("TestName1", 55443322L);
        Map.Entry<String, Long> test2 = Map.entry("TestName2", 711233L);
        HashMap<String, Long> expectedResponse = new HashMap<>(){};
        expectedResponse.put(test1.getKey(), test1.getValue());
        expectedResponse.put(test2.getKey(), test2.getValue());

        Integer days = 7;
        when(dbOutputTool.outTestSmellData(any(Timestamp.class))).thenReturn(expectedResponse);

        // When
        HashMap<String, Long> testSmells = dashboardService.getTestSmells(Optional.of(days), Optional.empty());

        // Then
        assertEquals(2, testSmells.size());
        assertTrue(testSmells.containsKey(test1.getKey()));
        assertTrue(testSmells.containsKey(test2.getKey()));
        assertEquals(test1.getValue(), testSmells.get(test1.getKey()));
        assertEquals(test2.getValue(), testSmells.get(test2.getKey()));
    }

    @Test
    public void getTestSmellsBySmellTypesSuccess() {
        // Given
        Map.Entry<String, Long> test1 = Map.entry("TestName1", 325678324L);
        Map.Entry<String, Long> test2 = Map.entry("TestName2", 7L);
        HashMap<String, Long> expectedResponse = new HashMap<>(){};
        expectedResponse.put(test1.getKey(), test1.getValue());
        expectedResponse.put(test2.getKey(), test2.getValue());

        ArrayList<String> smellTypes = new ArrayList<>();
        smellTypes.add("TestName1");
        smellTypes.add("TestName2");

        when(dbOutputTool.outTestSmellData(smellTypes)).thenReturn(expectedResponse);

        // When
        HashMap<String, Long> testSmells = dashboardService.getTestSmells(Optional.empty(), Optional.of(smellTypes));

        // Then
        assertEquals(2, testSmells.size());
        assertTrue(testSmells.containsKey(test1.getKey()));
        assertTrue(testSmells.containsKey(test2.getKey()));
        assertEquals(test1.getValue(), testSmells.get(test1.getKey()));
        assertEquals(test2.getValue(), testSmells.get(test2.getKey()));
    }

    @Test
    public void getTestSmellsBySmellTypesAndTimestampSuccess() {
        // Given
        Map.Entry<String, Long> test1 = Map.entry("TestName1", 123123L);
        Map.Entry<String, Long> test2 = Map.entry("TestName2", 522L);
        Map.Entry<String, Long> test3 = Map.entry("TestName3", 12L);
        Map.Entry<String, Long> test4 = Map.entry("TestName4", 3224L);
        HashMap<String, Long> expectedResponse = new HashMap<>(){};
        expectedResponse.put(test1.getKey(), test1.getValue());
        expectedResponse.put(test2.getKey(), test2.getValue());
        expectedResponse.put(test3.getKey(), test3.getValue());
        expectedResponse.put(test4.getKey(), test4.getValue());

        ArrayList<String> smellTypes = new ArrayList<>();
        smellTypes.add("TestName1");
        smellTypes.add("TestName2");
        smellTypes.add("TestName3");
        smellTypes.add("TestName4");

        Integer days = 30;
        when(dbOutputTool.outTestSmellData(any(Timestamp.class), eq(smellTypes))).thenReturn(expectedResponse);

        // When
        HashMap<String, Long> testSmells = dashboardService.getTestSmells(Optional.of(days), Optional.of(smellTypes));

        // Then
        assertEquals(4, testSmells.size());
        assertTrue(testSmells.containsKey(test1.getKey()));
        assertTrue(testSmells.containsKey(test2.getKey()));
        assertTrue(testSmells.containsKey(test3.getKey()));
        assertTrue(testSmells.containsKey(test4.getKey()));
        assertEquals(test1.getValue(), testSmells.get(test1.getKey()));
        assertEquals(test2.getValue(), testSmells.get(test2.getKey()));
        assertEquals(test3.getValue(), testSmells.get(test3.getKey()));
        assertEquals(test4.getValue(), testSmells.get(test4.getKey()));
    }

}
