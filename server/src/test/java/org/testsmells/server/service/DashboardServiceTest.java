package org.testsmells.server.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.testsmells.server.repository.DBOutputTool;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

@SpringBootTest
public class DashboardServiceTest {

    @MockBean
    DBOutputTool dbOutputTool;

    // Constant data for mocked call returns
    private final HashMap<String, Long> smellsEmpty = new HashMap<>();
    private HashMap<String, Long> smellsOne = new HashMap<>();

    @Test
    public void getTestSmellsAll() {
        // Given
        HashMap<String, Long> response = new HashMap<>(){};
        // When

        // Then
    }

}
