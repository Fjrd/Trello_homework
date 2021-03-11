package org.levelup.trello.jdbc;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcConnectionService {
    @SneakyThrows
    public Connection openConnection() throws SQLException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/application.properties"));
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        return DriverManager.getConnection(url, username, password);
    }
}
