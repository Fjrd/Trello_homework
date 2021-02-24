package org.levelup.trello.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnectionService {
    public Connection openConnection() throws SQLException {
        //jdbc:mysql://localhost:3306/trello
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/trello",
                "fjrd",
                "fjrd");
    }
}
