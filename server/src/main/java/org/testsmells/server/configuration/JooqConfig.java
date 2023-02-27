package org.testsmells.server.configuration;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JooqConfig {

    private final DatasourceConfig dsConfig;

    public JooqConfig(DatasourceConfig dsConfig) {
        this.dsConfig = dsConfig;
    }

    @Bean
    @Qualifier("dsl-dashboard")
    public DSLContext getDashboardDSL() {
        return DSL.using(dsConfig.getDashboardDataSource(), SQLDialect.MYSQL);
    }

    @Bean
    @Qualifier("dsl-plugin")
    public DSLContext getPluginDSL() {
        return DSL.using(dsConfig.getPluginDataSource(), SQLDialect.MYSQL);
    }


}
