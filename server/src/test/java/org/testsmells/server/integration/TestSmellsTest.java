package org.testsmells.server.integration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.MySQLContainer;
import org.testsmells.server.ServerApplication;
import org.testsmells.server.repository.DBOutputTool;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class containing end-to-end integration test(s) from top to bottom functionality (controller -> repository)
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        DBOutputTool.class,
        HikariDataSource.class,
        ServerApplication.class,
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestSmellsTest {

    private static final HikariConfig config = new HikariConfig();
    private static MySQLContainer<?> mysql;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Autowired
    @Qualifier("ds-dashboard")
    private HikariDataSource dataSource;

    @DynamicPropertySource
    static void setupDatasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.dashboard.jdbcUrl", mysql::getJdbcUrl);
        registry.add("spring.datasource.dashboard.username", mysql::getUsername);
        registry.add("spring.datasource.dashboard.password", mysql::getPassword);
    }

    @BeforeAll
    public static void suiteSetup() {
        mysql = new MySQLContainer<>("mysql:8.0");
        mysql.withDatabaseName("tsdetect");
        mysql.withInitScript("./init_test.sql");
        mysql.start();
    }

    @AfterAll
    public static void suiteTeardown() {
        mysql.close();
    }

    @Test
    public void getTestSmellsNoParamsSuccess() {
        // Given
        HashMap<String, Integer> expectedBody = new HashMap<>();
        expectedBody.put("Assertion Roulette", 25);
        expectedBody.put("Default Test", 30);
        expectedBody.put("Resource Optimism", 45);
        expectedBody.put("Exception Handling", 35);
        expectedBody.put("Magic Number Test", 40);

        UriComponentsBuilder request = UriComponentsBuilder.fromUriString("http://localhost:8080/test-smells");

        // When
        ResponseEntity<HashMap> response = restTemplate.getForEntity(request.build().encode().toUri(), HashMap.class);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBody, response.getBody());
    }

    @Test
    public void getTestSmellsAllParamsSuccess() {
        // Given
        HashMap<String, Integer> expectedBody = new HashMap<>();
        expectedBody.put("Default Test", 30);
        expectedBody.put("Exception Handling", 35);

        UriComponentsBuilder request = UriComponentsBuilder.fromUriString("http://localhost:8080/test-smells")
                .queryParam("datetime", 7)
                .queryParam("smell_type", Arrays.asList("Default Test", "Exception Handling"));

        // When
        ResponseEntity<HashMap> response = restTemplate.getForEntity(request.build().encode().toUri(), HashMap.class);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBody, response.getBody());
    }

}
