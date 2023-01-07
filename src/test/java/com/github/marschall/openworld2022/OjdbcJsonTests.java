package com.github.marschall.openworld2022;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.github.marschall.openworld2022.configuration.JdbcConfiguration;
import com.github.marschall.openworld2022.configuration.OracleConfiguration;

@SpringJUnitConfig({JdbcConfiguration.class, OracleConfiguration.class})
class OjdbcJsonTests {

  @Autowired
  private DataSource dataSource;

  @Autowired
  private JdbcOperations jdbcOperations;

  @Test
  void jsonGetObject() throws SQLException {
    try (var connection = this.dataSource.getConnection();
         var preparedStatement = connection.prepareStatement("""
             SELECT JSON('{"projectId": "NCA-029-0"}')
             FROM dual"""); // no longer needed on 23
         var resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {
        javax.json.JsonObject doc = resultSet.getObject(1, javax.json.JsonObject.class);
        // when only accessing parts JSON parsing in SQL is preferable
        assertEquals("NCA-029-0", doc.getString("projectId"));
      }
    }
  }

  @Test
  void jdbcTemplateJsonObject() {
    javax.json.JsonObject doc = this.jdbcOperations.queryForObject("""
             SELECT JSON('{"projectId": "NCA-029-0"}')
             FROM dual""", javax.json.JsonObject.class);
    assertEquals("NCA-029-0", doc.getString("projectId"));
  }

  @Test
  void jdbcTemplateParsing() {
    String projectId = this.jdbcOperations.queryForObject("""
             SELECT *
             FROM JSON_TABLE('{"projectId": "NCA-029-0"}',
                 '$' COLUMNS (projectId VARCHAR2(9)))""", String.class);
    assertEquals("NCA-029-0", projectId);
  }

}
