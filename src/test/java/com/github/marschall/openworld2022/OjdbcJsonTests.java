package com.github.marschall.openworld2022;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import javax.sql.DataSource;

class OjdbcJsonTests {

  private DataSource dataSource;
  
  void jsonValue() throws SQLException {
    try (var connection = dataSource.getConnection();
//         var preparedStatement = connection.prepareStatement("""
//             SELECT JSON('{"projectId": "NCA-029-0"}')
//             FROM dual"""); // no longer needed on 23
        var preparedStatement = connection.prepareStatement("""
             SELECT JSON({'projectId': 'NCA-029-0'})
             FROM dual"""); // no longer needed on 23
         var resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {
        javax.json.JsonObject doc = resultSet.getObject(1, javax.json.JsonObject.class);
        // when only accessing parts JSON parsing in SQL is preferable
        assertEquals("NCA-029-0", doc.getString("projectId"));
      }
    }
    
  }

}
