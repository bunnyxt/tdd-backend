package com.bunnyxt.tdd.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class DatabaseConfiguration {
    private final DataSourceProperties properties;

    public DatabaseConfiguration(DataSourceProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public void validate() {
        String url = properties.getUrl();
        if (url == null || !url.startsWith("jdbc:postgresql:") || url.contains("${")) {
            throw new IllegalStateException("Configure a PostgreSQL datasource URL before starting the service");
        }
        if (!configured(properties.getUsername()) || !configured(properties.getPassword())) {
            throw new IllegalStateException("Configure datasource credentials before starting the service");
        }
    }

    private static boolean configured(String value) {
        return value != null && !value.isEmpty() && !value.contains("${");
    }
}
