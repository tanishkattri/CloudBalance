package com.example.CloudBalance.utils.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class SnowFlakeConfig {

    @Value("${spring.snowflake.url}")
    private String url;

    @Value("${spring.snowflake.username}")
    private String username;

    @Value("${spring.snowflake.password}")
    private String password;

    @Value("${spring.snowflake.driver-class-name}")
    private String driverClassName;

    @Bean(name = "snowflakeDataSource")
    public DataSource snowflakeDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driverClassName);
        return ds;
    }

    @Bean(name = "snowflakeJdbcTemplate")
    public JdbcTemplate snowflakeJdbcTemplate(
            @Qualifier("snowflakeDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
