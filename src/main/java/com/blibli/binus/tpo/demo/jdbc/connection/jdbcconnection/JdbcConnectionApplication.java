package com.blibli.binus.tpo.demo.jdbc.connection.jdbcconnection;

// import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcConnectionApplication {

  // Driver & URL
  static final String JDBC_POSTGRESQL_DRIVER = "org.postgresql.Driver";

  static final String JDBC_POSTGRESQL_URL = "jdbc:postgresql://localhost/binus_jdbc";

  // Database Credentials
  static final String JDBC_POSTGRESQL_USERNAME = "postgres";
  static final String JDBC_POSTGRESQL_PASSWORD = "master";

  // INSERT SQL
  static final String INSERT_SQL =
    "INSERT INTO student (nim, nama, jurusan) VALUES ('1801448524', 'Jevon Averill', 'IT')";
  static final String SELECT_ALL_SQL = "SELECT * FROM student";
  static final String UPDATE_SQL =
    "UPDATE student SET nim = '1801413776', nama = 'Kevin Kurniawan' WHERE nim = '1801448524'";
  static final String DELETE_SQL = "DELETE FROM student WHERE nim = '1801413776'";

  // Using prepared statement
  static final String INSERT_SQL_USING_PREPARED_STATEMENT =
    "INSERT INTO student (nim, nama, jurusan) VALUES (?, ?, ?)";

  public static void main(String[] args) {
    Logger LOGGER = LoggerFactory.getLogger(JdbcConnectionApplication.class.getName());

    // Open a connection
    Connection connection = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
      // Register postgresql driver
      LOGGER.info("Register a postgresql driver...");
      Class.forName(JDBC_POSTGRESQL_DRIVER);

      LOGGER.info("Connecting to a database...");
      connection = DriverManager
        .getConnection(JDBC_POSTGRESQL_URL, JDBC_POSTGRESQL_USERNAME, JDBC_POSTGRESQL_PASSWORD);

      /* EXECUTE QUERY */

      // Create Statement
      LOGGER.info("Creating a statement...");
      statement = connection.createStatement();

      /* INSERT | UPDATE | DELETE */
      //      int result = statement.executeUpdate(UPDATE_SQL);
      //      if (result > 0) {
      //        LOGGER.info(result + " records updated");
      //      }

      /* INSERT using PREPARED STATEMENT */
      preparedStatement = connection.prepareStatement(INSERT_SQL_USING_PREPARED_STATEMENT);
      preparedStatement.setString(1, "1801448524");
      preparedStatement.setString(2, "Jevon Averill");
      preparedStatement.setString(3, "IT");
      int result = preparedStatement.executeUpdate();
      if (result > 0) {
        LOGGER.info(result + " records inserted");
      }

      /* SELECT ALL */
      resultSet = statement.executeQuery(SELECT_ALL_SQL);

      /* If we use this validation don't forget to use do while */
      //      if (!resultSet.next()) {
      //        LOGGER.warn("no data found...");
      //      }
      //      do {
      //        LOGGER.info("nim: {}", resultSet.getString("nim"));
      //        LOGGER.info("nama: {}", resultSet.getString("nama"));
      //        LOGGER.info("jurusan: {}", resultSet.getString("jurusan"));
      //      } while (resultSet.next());

      while (resultSet.next()) {
        LOGGER.info("nim: {}", resultSet.getString("nim"));
        LOGGER.info("nama: {}", resultSet.getString("nama"));
        LOGGER.info("jurusan: {}", resultSet.getString("jurusan"));
      }
    } catch (Exception e) {
      LOGGER.error("error: {}", e.getMessage(), e);
    } finally {
      try {
        // Close connection | statement | resultSet
        if (connection != null) {
          connection.close();
        }
        if (statement != null) {
          statement.close();
        }
        if (resultSet != null) {
          resultSet.close();
        }
        if (preparedStatement != null) {
          preparedStatement.close();
        }
      } catch (SQLException sqle) {
        LOGGER.error("error sqlexception");
      }
    }
  }

}
