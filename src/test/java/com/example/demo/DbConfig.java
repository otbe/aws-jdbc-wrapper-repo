package com.example.demo;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
public class DbConfig {
  @Bean
  public JdbcDatabaseContainer<?> monogDbContainer(DynamicPropertyRegistry properties) {
    JdbcDatabaseContainer<?> container = new PostgreSQLContainer("postgres:15.3");
    properties.add(
        "spring.datasource.url",
        () ->
            "jdbc:aws-wrapper:postgresql://"
                + container.getHost()
                + ":"
                + container.getMappedPort(5432)
                + "/postgres?user="
                + container.getUsername()
                + "&password="
                + container.getPassword()
                + "&wrapperPlugins=driverMetaData&wrapperDriverName=PostgreSQL JDBC Driver&wrapperDialect=pg");
    return container;
  }
}
