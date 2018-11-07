package com.airline.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@SpringBootApplication
public class App {
  static String password = null;
  private static final String url = "jdbc:mysql://" + System.getenv("MYSQL_DATABASE_HOST") + ":" +
      System.getenv("MYSQL_DATABASE_PORT") + "/" + System.getenv("MYSQL_DATABASE");

  public static void main(String[] args) throws InterruptedException {
    File passwordSecret = new File(System.getenv("MYSQL_PASSWORD_FILE"));
    byte[] passwordBytes = new byte[0];
    try {
      passwordBytes = Files.readAllBytes(passwordSecret.toPath());
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
    password = new String(passwordBytes);
    boolean connectionAvailable = false;
    Properties connectionProps = new Properties();
    connectionProps.setProperty("user", System.getenv("MYSQL_USER"));
    connectionProps.setProperty("password", password);
    while (!connectionAvailable) {
      try {
        DriverManager.getConnection(url, connectionProps);
        connectionAvailable = true;
      } catch (SQLException e) {
        System.err.println("Waiting for database to up");
        Thread.sleep(2000);
      }
    }

    SpringApplication.run(App.class, args);
  }
}
