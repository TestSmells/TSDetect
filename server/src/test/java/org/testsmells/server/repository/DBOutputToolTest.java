package org.testsmells.server.repository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.jooq.impl.QOM;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.MySQLContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.testcontainers.containers.MySQLContainer;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * JUnit tests of the DBOutputTool
 * All tests use the various outTestSmellData functions
 * ----------------------------------------------------------------------------------
 * The init_test.sql script in the resources folder is the same
 * script that initializes the database (TSDetect/database/tsdetect-mysql/).
 * ----------------------------------------------------------------------------------
 * Before running the tests:
 *  - Verify the init_test.sql files are identical
 *  - Remove or comment out the CREATE USER statements in the script
 */

class DBOutputToolTest {
    private static final HikariConfig config = new HikariConfig();

    private static MySQLContainer<?> mysql;
    private static HikariDataSource ds;
    private static DBOutputTool outputTool;

    private HashMap<String, Long> expected;
    private ArrayList<String> smells;
    private LocalDate localDate;
    private Timestamp timestamp;

    @BeforeAll
    public static void suiteSetup() {
        mysql = new MySQLContainer<>("mysql:8.0");
        mysql.withDatabaseName("tsdetect");
        mysql.withInitScript("./init_test.sql");
        mysql.start();
        config.setJdbcUrl(mysql.getJdbcUrl());
        config.setUsername(mysql.getUsername());
        config.setPassword(mysql.getPassword());
    }

    @AfterAll
    public static void suiteTeardown() {
        ds.close();
        mysql.close();
    }

    @BeforeEach
    protected void setup() {
        expected = new HashMap<>();
        smells = new ArrayList<>();
        localDate = null;
        timestamp = null;
        ds = new HikariDataSource(config);
        outputTool = new DBOutputTool(ds);
    }

    @AfterEach
    protected void teardown() {
        ds.close();
        outputTool = null;
    }

    @Test
    void allSmells() {
        expected.put(Constants.ASSERTION_ROULETTE, 25L);
        expected.put(Constants.DEFAULT_TEST, 30L);
        expected.put(Constants.EXCEPTION_HANDLING, 35L);
        expected.put(Constants.MAGIC_NUMBER_TEST, 40L);
        expected.put(Constants.RESOURCE_OPTIMISM, 45L);
        assertEquals(expected, outputTool.outTestSmellData());
    }

    @Test
    void singleSmell() {
        expected.put(Constants.DEFAULT_TEST, 30L);
        smells.add(Constants.DEFAULT_TEST);
        assertEquals(expected, outputTool.outTestSmellData(smells));
    }

    @Test
    void multipleSmells() {
        expected.put(Constants.DEFAULT_TEST, 30L);
        expected.put(Constants.EXCEPTION_HANDLING, 35L);
        expected.put(Constants.RESOURCE_OPTIMISM, 45L);
        smells.add(Constants.DEFAULT_TEST);
        smells.add(Constants.EXCEPTION_HANDLING);
        smells.add(Constants.RESOURCE_OPTIMISM);
        assertEquals(expected, outputTool.outTestSmellData(smells));
    }

    //STRING TIMESTAMP TESTS
    @Test
    void singleSmellFromStringTimestamp() {
        expected.put(Constants.ASSERTION_ROULETTE, 25L);
        smells.add(Constants.ASSERTION_ROULETTE);
        assertEquals(expected, outputTool.outTestSmellData("2021-01-01", smells));
    }

    @Test
    void multipleSmellsFromStringTimestamp() {
        expected.put(Constants.ASSERTION_ROULETTE, 25L);
        expected.put(Constants.DEFAULT_TEST, 30L);
        smells.add(Constants.ASSERTION_ROULETTE);
        smells.add(Constants.DEFAULT_TEST);
        assertEquals(expected, outputTool.outTestSmellData("2021-01-01", smells));
    }

    @Test
    void noSmellsFromStringTimestamp() {
        assertEquals(expected, outputTool.outTestSmellData("2021-01-01", smells));
    }

    @Test
    void allFromStringTimestamp() {
        expected.put(Constants.ASSERTION_ROULETTE, 25L);
        expected.put(Constants.DEFAULT_TEST, 30L);
        expected.put(Constants.EXCEPTION_HANDLING, 35L);
        expected.put(Constants.MAGIC_NUMBER_TEST, 40L);
        expected.put(Constants.RESOURCE_OPTIMISM, 45L);
        assertEquals(expected, outputTool.outTestSmellData("2021-01-01"));
    }

    @Test
    void dayFromStringTimestamp() {
        expected.put(Constants.ASSERTION_ROULETTE, 25L);
        expected.put(Constants.DEFAULT_TEST, 30L);
        //Current time minus 1 day
        localDate = LocalDate.now().minusDays(1);
        assertEquals(expected, outputTool.outTestSmellData(localDate.toString()));
    }

    @Test
    void weekFromStringTimestamp() {
        expected.put(Constants.ASSERTION_ROULETTE, 25L);
        expected.put(Constants.DEFAULT_TEST, 30L);
        expected.put(Constants.EXCEPTION_HANDLING, 35L);
        //Current time minus 1 week
        localDate = LocalDate.now().minusWeeks(1);
        assertEquals(expected, outputTool.outTestSmellData(localDate.toString()));
    }

    @Test
    void monthFromStringTimestamp() {
        expected.put(Constants.ASSERTION_ROULETTE, 25L);
        expected.put(Constants.DEFAULT_TEST, 30L);
        expected.put(Constants.EXCEPTION_HANDLING, 35L);
        expected.put(Constants.MAGIC_NUMBER_TEST, 40L);
        //Current time minus 1 month
        localDate = LocalDate.now().minusMonths(1);
        assertEquals(expected, outputTool.outTestSmellData(localDate.toString()));
    }

    @Test
    void yearFromStringTimestamp() {
        expected.put(Constants.ASSERTION_ROULETTE, 25L);
        expected.put(Constants.DEFAULT_TEST, 30L);
        expected.put(Constants.EXCEPTION_HANDLING, 35L);
        expected.put(Constants.MAGIC_NUMBER_TEST, 40L);
        expected.put(Constants.RESOURCE_OPTIMISM, 45L);
        //Current time minus 1 year
        localDate = LocalDate.now().minusYears(1);
        assertEquals(expected, outputTool.outTestSmellData(localDate.toString()));
    }

    @Test
    void futureStringTimestamp() {
        assertEquals(expected, outputTool.outTestSmellData("2024-01-01"));
    }

    @Test
    void invalidStringTimestamp() {
        assertEquals(expected, outputTool.outTestSmellData("thisisnotadate"));
    }

    //JAVA TIMESTAMP TESTS
    @Test
    void singleSmellFromJavaTimestamp() {
        expected.put(Constants.ASSERTION_ROULETTE, 25L);
        smells.add(Constants.ASSERTION_ROULETTE);
        assertEquals(expected, outputTool.outTestSmellData(new Timestamp(1609477200), smells));
    }

    @Test
    void multipleSmellsFromJavaTimestamp() {
        expected.put(Constants.ASSERTION_ROULETTE, 25L);
        expected.put(Constants.RESOURCE_OPTIMISM, 45L);
        smells.add(Constants.ASSERTION_ROULETTE);
        smells.add(Constants.RESOURCE_OPTIMISM);
        assertEquals(expected, outputTool.outTestSmellData(new Timestamp(1609477200), smells));
    }

    @Test
    void noSmellsFromJavaTimestamp() {
        assertEquals(expected, outputTool.outTestSmellData(new Timestamp(1609477200), smells));
    }

    @Test
    void allFromJavaTimestamp() {
        expected.put(Constants.ASSERTION_ROULETTE, 25L);
        expected.put(Constants.DEFAULT_TEST, 30L);
        expected.put(Constants.EXCEPTION_HANDLING, 35L);
        expected.put(Constants.MAGIC_NUMBER_TEST, 40L);
        expected.put(Constants.RESOURCE_OPTIMISM, 45L);
        assertEquals(expected, outputTool.outTestSmellData(new Timestamp(1609477200)));
    }

    @Test
    void dayFromJavaTimestamp() {
        expected.put(Constants.ASSERTION_ROULETTE, 25L);
        expected.put(Constants.DEFAULT_TEST, 30L);
        //Current time minus 1 day
        timestamp = new Timestamp(System.currentTimeMillis()-86400000L); //1 day in ms
        assertEquals(expected, outputTool.outTestSmellData(timestamp));
    }

    @Test
    void weekFromJavaTimestamp() {
        expected.put(Constants.ASSERTION_ROULETTE, 25L);
        expected.put(Constants.DEFAULT_TEST, 30L);
        expected.put(Constants.EXCEPTION_HANDLING, 35L);
        //Current time minus 1 week
        timestamp = new Timestamp(System.currentTimeMillis()-(86400000L*7));
        assertEquals(expected, outputTool.outTestSmellData(timestamp));
    }

    @Test
    void monthFromJavaTimestamp() {
        expected.put(Constants.ASSERTION_ROULETTE, 25L);
        expected.put(Constants.DEFAULT_TEST, 30L);
        expected.put(Constants.EXCEPTION_HANDLING, 35L);
        expected.put(Constants.MAGIC_NUMBER_TEST, 40L);
        //Current time minus 1 month
        timestamp = new Timestamp(System.currentTimeMillis()-(86400000L*30));
        assertEquals(expected, outputTool.outTestSmellData(timestamp));
    }

    @Test
    void yearFromJavaTimestamp() {
        expected.put(Constants.ASSERTION_ROULETTE, 25L);
        expected.put(Constants.DEFAULT_TEST, 30L);
        expected.put(Constants.EXCEPTION_HANDLING, 35L);
        expected.put(Constants.MAGIC_NUMBER_TEST, 40L);
        expected.put(Constants.RESOURCE_OPTIMISM, 45L);
        //Current time minus 1 year
        timestamp = new Timestamp(System.currentTimeMillis()-(86400000L*365));
        assertEquals(expected, outputTool.outTestSmellData(timestamp));
    }

    @Test
    void futureJavaTimestamp() {
        assertEquals(expected, outputTool.outTestSmellData(new Timestamp(Long.MAX_VALUE/50000)));
    }

    @Test
    void getAllTestRunIDsFromDateInvalidDatasourceFails() throws SQLException {
        // Given
        ds = mock(HikariDataSource.class);
        outputTool = new DBOutputTool(ds);
        when(ds.getConnection()).thenThrow(new SQLException());

        // When
        HashMap<String, Long> response = outputTool.outTestSmellData();

        // Then
        assertEquals(0, response.size());
    }

    @Test
    void outTestSmellDataInvalidDatasourceGivesEmptyResults() throws SQLException {
        // Given
        ds = mock(HikariDataSource.class);
        outputTool = new DBOutputTool(ds);
        when(ds.getConnection()).thenThrow(new SQLException());

        // When
        HashMap<String, Long> responseNoParams = outputTool.outTestSmellData();
        HashMap<String, Long> responseSmells = outputTool.outTestSmellData(new ArrayList<>());
        HashMap<String, Long> responseTimestamp = outputTool.outTestSmellData(new Timestamp(1));
        HashMap<String, Long> responseDateString = outputTool.outTestSmellData("randomStartDate");
        HashMap<String, Long> responseDateStringAndSmells = outputTool.outTestSmellData("randomStartDate", new ArrayList<>());
        HashMap<String, Long> responseTimestampAndSmells = outputTool.outTestSmellData(new Timestamp(1231), new ArrayList<>());

        // Then
        assertEquals(0, responseNoParams.size());
        assertEquals(0, responseSmells.size());
        assertEquals(0, responseTimestamp.size());
        assertEquals(0, responseDateString.size());
        assertEquals(0, responseDateStringAndSmells.size());
        assertEquals(0, responseTimestampAndSmells.size());
    }
}
