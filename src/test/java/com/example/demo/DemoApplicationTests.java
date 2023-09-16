package com.example.demo;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.postgresql.jdbc.PgConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.DriverManager;
import java.util.LinkedList;
import java.util.List;

@Testcontainers
@SpringBootTest
@Import(DbConfig.class)
class DemoApplicationTests {
  @Autowired JdbcTemplate jdbcTemplate;

  @Value("${spring.datasource.url}")
  String url;

  @Test
  @SneakyThrows
  void failingTest() {
    // works
    jdbcTemplate.execute("select 42;");

    try (var pgConnection = DriverManager.getConnection(url).unwrap(PgConnection.class)) {
      // works
      pgConnection.createStatement().executeQuery("select 42;");

      // a little bit of memory pressure to trigger GC
      List<byte[]> list = new LinkedList<>();
      int index = 1;
      while (index < 20) {
        byte[] b = new byte[10 * 1024 * 1024]; // 10MB byte object
        list.add(b);
        index++;
      }

      // breaks
      pgConnection.createStatement().executeQuery("select 42;");
    }
  }
}
