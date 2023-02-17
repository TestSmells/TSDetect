package org.testsmells.server;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testsmells.server.repository.Constants;
import org.testsmells.server.repository.DBInputTool;

import java.sql.Timestamp;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * JUnit tests of the DBInputTool
 * All tests use the inputData function
 * ----------------------------------------------------------------------------------
 * The init_test.sql script in the resources folder is the same
 * script that initializes the database (TSDetect/database/tsdetect-mysql/).
 * ----------------------------------------------------------------------------------
 * Before running the tests:
 *  - Verify the init_test.sql files are identical
 *  - Remove or comment out the CREATE USER statements in the script
 */
class DBInputToolTest {
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0");
        mysql.withDatabaseName("tsdetect");
        mysql.withInitScript("./init_test.sql");
        mysql.start();
        config.setJdbcUrl(mysql.getJdbcUrl());
        config.setUsername(mysql.getUsername());
        config.setPassword(mysql.getPassword());
        ds = new HikariDataSource(config);
    }

    private static DBInputTool inputTool;
    private HashMap<String, Integer> smells;
    private HashMap<String, Integer> expected;
    private Timestamp timestamp;

    @BeforeEach
    protected void setUp() {
        inputTool = new DBInputTool(DSL.using(ds, SQLDialect.MYSQL), ds);
        expected = new HashMap<>();
        smells = new HashMap<>();
        timestamp = new Timestamp(0);
    }

    @Test
    void singleSmell() {
        smells.put(Constants.ASSERTION_ROULETTE, 1);
        expected = new HashMap<>(smells);
        expected.put("singleSmell", 1);
        assertEquals(expected, inputTool.inputData("singleSmell", timestamp, smells));
    }

    @Test
    void multipleSmells() {
        smells.put(Constants.ASSERTION_ROULETTE, 1);
        smells.put(Constants.DEFAULT_TEST, 2);
        smells.put(Constants.EXCEPTION_HANDLING, 3);
        expected = new HashMap<>(smells);
        expected.put("multipleSmells", 1);
        assertEquals(expected, inputTool.inputData("multipleSmells", timestamp, smells));
    }

    @Test
    void allSmells() {
        smells.put(Constants.ASSERTION_ROULETTE, 1);
        smells.put(Constants.CONDITIONAL_TEST_LOGIC, 2);
        smells.put(Constants.CONSTRUCTOR_INITIALIZATION, 3);
        smells.put(Constants.DEFAULT_TEST, 4);
        smells.put(Constants.DUPLICATE_ASSERT, 5);
        smells.put(Constants.EAGER_TEST, 6);
        smells.put(Constants.EMPTY_TEST, 7);
        smells.put(Constants.EXCEPTION_HANDLING, 8);
        smells.put(Constants.GENERAL_FIXTURE, 9);
        smells.put(Constants.IGNORED_TEST, 10);
        smells.put(Constants.LAZY_TEST, 11);
        smells.put(Constants.MAGIC_NUMBER_TEST, 12);
        smells.put(Constants.MYSTERY_GUEST, 13);
        smells.put(Constants.REDUNDANT_PRINT, 14);
        smells.put(Constants.REDUNDANT_ASSERTION, 15);
        smells.put(Constants.RESOURCE_OPTIMISM, 16);
        smells.put(Constants.SENSITIVE_EQUALITY, 17);
        smells.put(Constants.SLEEPY_TEST, 18);
        smells.put(Constants.UNKNOWN_TEST, 19);
        expected = new HashMap<>(smells);
        expected.put("allSmells", 1);
        assertEquals(expected, inputTool.inputData("allSmells", timestamp, smells));
    }

    @Test
    void noSmells() {
        expected = new HashMap<>(smells);
        expected.put("noSmells", 0);
        assertEquals(expected, inputTool.inputData("noSmells", timestamp, smells));
    }

    @Test
    void smellCountMax() {
        smells.put(Constants.ASSERTION_ROULETTE, 2147483647);
        expected = new HashMap<>(smells);
        expected.put("smellCountMax", 1);
        assertEquals(expected, inputTool.inputData("smellCountMax", timestamp, smells));
    }

    @Test
    void uidEmpty() {
        smells.put(Constants.ASSERTION_ROULETTE, 1);
        expected.put("", 0);
        assertEquals(expected, inputTool.inputData("", timestamp, smells));
    }

    @Test
    void uidMaxLength() {
        smells.put(Constants.ASSERTION_ROULETTE, 1);
        expected = new HashMap<>(smells);
        expected.put("01234567890123456789012345678901234567890123456789", 1);
        assertEquals(expected, inputTool.inputData("01234567890123456789012345678901234567890123456789", timestamp, smells));
    }

    @Test
    //MySQL min timestamp is 1000-01-01 00:00:00 or -30610206238
    void timestampMin() {
        smells.put(Constants.ASSERTION_ROULETTE, 1);
        expected = new HashMap<>(smells);
        expected.put("timestampMin", 1);
        assertEquals(expected, inputTool.inputData("timestampMin", new Timestamp(-30610206238L), smells));
    }

    @Test
    //MySQL max timestamp is 9999-12-31 23:59:59 or 253402318799
    void timestampMax() {
        smells.put(Constants.ASSERTION_ROULETTE, 1);
        expected = new HashMap<>(smells);
        expected.put("timestampMaxLength", 1);
        assertEquals(expected, inputTool.inputData("timestampMaxLength", new Timestamp(253402318799L), smells));
    }

    @Test
    void timestampUnderMin() {
        smells.put(Constants.ASSERTION_ROULETTE, 1);
        expected.put("timestampUnderMin", 0);
        assertEquals(expected, inputTool.inputData("timestampUnderMin", new Timestamp(Long.MIN_VALUE), smells));
    }
}
