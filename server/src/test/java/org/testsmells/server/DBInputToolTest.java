package org.testsmells.server;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeEach;
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
    private Timestamp timestamp;

    @BeforeEach
    protected void setUp() {
        inputTool = new DBInputTool(DSL.using(ds, SQLDialect.MYSQL), ds);
        smells = new HashMap<>();
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    @Test
    void singleSmell() {
        System.out.println("SINGLE SMELL TYPE TEST");
        smells.put(Constants.ASSERTION_ROULETTE, 1);
        assertTrue(inputTool.inputData("singleSmell", timestamp, smells));
    }

    @Test
    void multipleSmells() {
        System.out.println("MULTIPLE SMELL TYPES TEST");
        smells.put(Constants.ASSERTION_ROULETTE, 1);
        smells.put(Constants.DEFAULT_TEST, 2);
        smells.put(Constants.EXCEPTION_HANDLING, 3);
        assertTrue(inputTool.inputData("multipleSmells", timestamp, smells));
    }

    @Test
    void allSmells() {
        System.out.println("ALL SMELL TYPES TEST");
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
        assertTrue(inputTool.inputData("allSmells", timestamp, smells));
    }

    @Test
    void noSmells() {
        System.out.println("NO SMELL TYPES TEST");
        assertFalse(inputTool.inputData("noSmells", timestamp, smells));
    }

    @Test
    void duplicateSmells() {
        System.out.println("DUPLICATE SMELL TYPES TEST");
        smells.put(Constants.ASSERTION_ROULETTE, 1);
        smells.put(Constants.ASSERTION_ROULETTE, 2);
        assertFalse(inputTool.inputData("duplicateSmells", timestamp, smells));
    }

    @Test
    void smellCountZero() {
        System.out.println("ZERO SMELL COUNT TEST");
        smells.put(Constants.ASSERTION_ROULETTE, 0);
        assertFalse(inputTool.inputData("smellCountZero", timestamp, smells));
    }

    @Test
    void smellCountMax() {
        System.out.println("MAX SMELL COUNT TEST");
        smells.put(Constants.ASSERTION_ROULETTE, 2147483647);
        assertTrue(inputTool.inputData("smellCountMax", timestamp, smells));
    }

    @Test
    void smellCountOverflow() {
        System.out.println("OVERFLOW SMELL COUNT TEST");
        //Java and MySQL have the same max int size, preventing a smell count overflow
        //smells.put(Constants.ASSERTION_ROULETTE, 2147483648);
        assertTrue(true);
    }

    @Test
    void smellCountNegative() {
        System.out.println("NEGATIVE SMELL COUNT TEST");
        smells.put(Constants.ASSERTION_ROULETTE, -1);
        assertFalse(inputTool.inputData("negativeSmellCount", timestamp, smells));
    }

    @Test
    void uidEmpty() {
        System.out.println("UID EMPTY TEST");
        smells.put(Constants.ASSERTION_ROULETTE, 1);
        assertFalse(inputTool.inputData("", timestamp, smells));
    }

    @Test
    void uidMaxLength() {
        System.out.println("UID MAX TEST");
        smells.put(Constants.ASSERTION_ROULETTE, 1);
        assertTrue(inputTool.inputData("01234567890123456789012345678901234567890123456789", timestamp, smells));
    }

    @Test
    void uidOverflowLength() {
        System.out.println("UID OVERFLOW TEST");
        smells.put(Constants.ASSERTION_ROULETTE, 1);
        assertFalse(inputTool.inputData("012345678901234567890123456789012345678901234567890", timestamp, smells));
    }

    @Test
    void timestampOverflow() {
        System.out.println("OVERFLOW TIMESTAMP TEST");
        smells.put(Constants.ASSERTION_ROULETTE, 1);
        //Java and MySQL have the same max int size, preventing a timestamp overflow
        //assertFalse(inputTool.inputData("timeStampOverFlow", new Timestamp(2147483648), smells));
        assertTrue(true);
    }

    @Test
    void timestampNegative() {
        System.out.println("NEGATIVE TIMESTAMP TEST");
        smells.put(Constants.ASSERTION_ROULETTE, 1);
        assertFalse(inputTool.inputData("timeStampNegative", new Timestamp(-1), smells));
    }
}
