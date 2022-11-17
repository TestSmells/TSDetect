package org.testsmells.server.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DatasourceConfig {

    @Bean
    @Qualifier("ds-plugin")
    @Primary
    @ConfigurationProperties("spring.datasource.plugin")
    public HikariDataSource getPluginDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @Qualifier("ds-dashboard")
    @ConfigurationProperties("spring.datasource.dashboard")
    public HikariDataSource getDashboardDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

}
