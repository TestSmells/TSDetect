package org.testsmells.server;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testsmells.server.repository.Constants;
import org.testsmells.server.repository.DBOutputTool;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JUnit tests of the DBOutputTool
 * All tests use the various outTestSmellData functions
 * Run these tests within half a day of setting up the database
 *  to ensure time restrictions are met
 * ----------------------------------------------------------------------------------
 * Set up:
 *   - Open docker_compose.yml in TSDetect/database/tsdetect-mysql/
 *     - Under volumes, replace ./init.sql:/docker... with ./init_test.sql:/docker...
 *   - Use a terminal to run 'docker compose up -d' in the same directory
 * ----------------------------------------------------------------------------------
 * Reset MySQL database:
 *   - Run 'docker compose down' followed by 'docker compose up -d'
 * ----------------------------------------------------------------------------------
 * Test Data in MySQL Database:
 *   test_runs(user1, NOW, 1)            test_run_smells(1, 1, 25)
 *   test_runs(user2, NOW-0.5 days, 2)   test_run_smells(2, 4, 30)
 *   test_runs(user3, NOW-6 days, 3)     test_run_smells(3, 8, 35)
 *   test_runs(user4, NOW-29 days, 4)    test_run_smells(4, 12, 40)
 *   test_runs(user5, NOW-364 days, 5)   test_run_smells(5, 16, 45)
 */
class DBOutputToolTest {
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        config.setJdbcUrl("jdbc:mysql://localhost:3308/tsdetect");
        config.setUsername("dashboard");
        config.setPassword("password");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    private DBOutputTool outputTool;
    private HashMap<String, Long> expected;
    private ArrayList<String> smells;
    private LocalDate localDate;
    private Timestamp timestamp;

    @BeforeEach
    protected void setUp() {
        outputTool = new DBOutputTool(DSL.using(ds, SQLDialect.MYSQL), ds);
        expected = new HashMap<>();
        smells = new ArrayList<>();
        localDate = null;
        timestamp = null;
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
    void invalidJavaTimestamp() {
        assertEquals(expected, outputTool.outTestSmellData(new Timestamp(Long.MAX_VALUE+1)));
    }
}
