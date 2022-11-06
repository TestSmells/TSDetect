package org.testsmells.server.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatasourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource")
    public HikariDataSource getDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

}
