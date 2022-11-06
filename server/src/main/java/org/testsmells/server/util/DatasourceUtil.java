package org.testsmells.server.util;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testsmells.server.configuration.DatasourceConfig;

import java.sql.SQLException;

@Component
public class DatasourceUtil {

    @Autowired
    private DatasourceConfig dsConfig;

    public DSLContext getDSLContext() throws SQLException {
        return DSL.using(dsConfig.getDataSource().getConnection(), SQLDialect.MYSQL);
    }

}
