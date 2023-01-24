package org.testsmells.server;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;
import org.testsmells.server.repository.Constants;
import org.testsmells.server.repository.DBOutputTool;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Before running the following tests for the DBOutputTool, ensure the MySQL database has been
 * initialized with the init_test.sql file. On line 15 of the database docker_compose.yml,
 * change the first instance of init.sql to init_test.sql
 */
class DBOutputToolTest {
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

    private final DBOutputTool outputTool = new DBOutputTool(DSL.using(ds, SQLDialect.MYSQL), ds);

    @Test
    void verifyAssertionRoulette() {
        assertEquals(25, outputTool.outTestSmellData().get(Constants.ASSERTION_ROULETTE));
    }

    @Test
    void verifyDefaultTest() {
        assertEquals(30, outputTool.outTestSmellData().get(Constants.DEFAULT_TEST));
    }

    @Test
    void verifyExceptionHandling() {
        assertEquals(35, outputTool.outTestSmellData().get(Constants.EXCEPTION_HANDLING));
    }

    @Test
    void verifyMagicNumberTest() {
        assertEquals(40, outputTool.outTestSmellData().get(Constants.MAGIC_NUMBER_TEST));
    }

    @Test
    void verifyResourceOptimism() {
        assertEquals(45, outputTool.outTestSmellData().get(Constants.RESOURCE_OPTIMISM));
    }
}
