package org.levelup.trello.service.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnectionService {
    public Connection openConnection() throws SQLException {
        //TODO add property file
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/trello",
                "fjrd",
                "fjrd");
    }
}
