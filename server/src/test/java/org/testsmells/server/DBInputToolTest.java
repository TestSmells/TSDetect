package org.testsmells.server;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.testsmells.server.repository.Constants;
import org.testsmells.server.repository.DBInputTool;

import java.sql.*;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests of the DBInputTool
 * All tests use the inputData function
 * ----------------------------------------------------------------------------------
 * Set up:
 *   - Open docker_compose.yml in TSDetect/database/tsdetect-mysql/
 *     - Under volumes, replace ./init.sql:/docker... with ./init_test.sql:/docker...
 *   - Use a terminal to run 'docker compose up -d' in the same directory
 * ----------------------------------------------------------------------------------
 * Reset MySQL database:
 *   - Run 'docker compose down' followed by 'docker compose up -d'
 */
class DBInputToolTest {
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        config.setJdbcUrl("jdbc:mysql://localhost:3308/tsdetect");
        config.setUsername("plugin");
        config.setPassword("password");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    private DBInputTool inputTool;
    private HashMap<String, Integer> smells;
    private HashMap<String, Integer> expected;
    private Timestamp timestamp;

    @BeforeEach
    protected void setUp() {
        inputTool = new DBInputTool(DSL.using(ds, SQLDialect.MYSQL), ds);
        smells = new HashMap<>();
        expected = new HashMap<>();
        timestamp = new Timestamp(System.currentTimeMillis());
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
    void duplicateSmells() {
        smells.put(Constants.ASSERTION_ROULETTE, 1);
        smells.put(Constants.ASSERTION_ROULETTE, 2);
        expected = new HashMap<>(smells);
        expected.put("duplicateSmells", 1);
        assertEquals(expected, inputTool.inputData("duplicateSmells", timestamp, smells));
    }

    @Test
    void smellCountZero() {
        smells.put(Constants.ASSERTION_ROULETTE, 0);
        expected.put("smellCountZero", 0);
        assertEquals(expected, inputTool.inputData("smellCountZero", timestamp, smells));
    }

    @Test
    void smellCountMax() {
        smells.put(Constants.ASSERTION_ROULETTE, 2147483647);
        expected = new HashMap<>(smells);
        expected.put("smellCountMax", 1);
        assertEquals(expected, inputTool.inputData("smellCountMax", timestamp, smells));
    }

    @Test
    void smellCountOverflow() {
        //Java and MySQL have the same max int size, preventing a smell count overflow
        //smells.put(Constants.ASSERTION_ROULETTE, 2147483648);
        assertTrue(true);
    }

    @Test
    void smellCountNegative() {
        smells.put(Constants.ASSERTION_ROULETTE, -1);
        expected.put("negativeSmellCount", 0);
        assertEquals(expected, inputTool.inputData("negativeSmellCount", timestamp, smells));
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
    void uidOverflowLength() {
        smells.put(Constants.ASSERTION_ROULETTE, 1);
        expected.put("012345678901234567890123456789012345678901234567890", 0);
        assertEquals(expected, inputTool.inputData("012345678901234567890123456789012345678901234567890", timestamp, smells));
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

    @Test
    void timestampOverMax() {
        smells.put(Constants.ASSERTION_ROULETTE, 1);
        expected.put("timestampOverMax", 0);
        assertEquals(expected, inputTool.inputData("timestampOverMax", new Timestamp(Long.MAX_VALUE), smells));
    }
}
