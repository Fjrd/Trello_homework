package org.levelup.trello.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class for creating connection to database using JDBC.
 * JDBC - Java Database Connectivity
 *
 * DriverManager - управляет всеми драйверами в приложении
 */
public class JdbcConnectionService {

    // Ручная загрузка драйвера в память и регистрация его в DriverManager
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Open new connection to database
     * @return connection
     */
    public Connection openConnection() throws SQLException {
        // getConnection(String url, String username, String password)
        // URL:
        //  jdbc:<vendor_name>://<host(ip_address)>:<port>/<database_name>?<attr1>=<val1>&<attr2>=<val2>
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/trello",
                "postgres",
                "root"
        );
    }

}
