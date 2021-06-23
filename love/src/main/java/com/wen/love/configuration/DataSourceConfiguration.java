package com.wen.love.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {


    @Bean("dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource getDataSource(){
        return DataSourceBuilder.create().build();
    }
}
