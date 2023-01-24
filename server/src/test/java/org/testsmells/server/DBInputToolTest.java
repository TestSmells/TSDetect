package org.testsmells.server;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;
import org.testsmells.server.repository.Constants;
import org.testsmells.server.repository.DBInputTool;

import java.sql.*;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DBInputToolTest {
    private final HashMap<String, Integer> smells = new HashMap<>();
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        config.setJdbcUrl("jdbc:mysql://localhost:3308/tsdetect");
        config.setUsername("root");
        config.setPassword("password");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    private final DBInputTool inputTool = new DBInputTool(DSL.using(ds, SQLDialect.MYSQL), ds);

    @Test
    void insertData() {
        smells.put(Constants.ASSERTION_ROULETTE, 5);
        assertTrue(inputTool.inputData("test", new Timestamp(1672592400), smells));
    }
}
