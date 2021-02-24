package org.levelup.trello.service.jdbc;

import org.levelup.trello.profiling.AliveTime;
import org.levelup.trello.profiling.OpenConnectionServiceInvocationHandler;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class for creating connection to database using JDBC.
 * JDBC - Java Database Connectivity
 *
 * DriverManager - управляет всеми драйверами в приложении
 */
public class JdbcConnectionService implements ConnectionService {

    // Ручная загрузка драйвера в память и регистрация его в DriverManager
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static ConnectionService buildJdbcConnectionService() {
        // SomeInterface
        //  doSmth();

        // A, ProxyS, S
        // A использует внутри себя класс S
        // A думает, что использует S, но на самом деле работает через ProxyS
        // A -> ProxyS -> S
        return (ConnectionService) Proxy.newProxyInstance(
                JdbcConnectionService.class.getClassLoader(), // класслоадер который загрузил наш класс
                JdbcConnectionService.class.getInterfaces(), // список интерфейсов, которые наш класс реализует
                new OpenConnectionServiceInvocationHandler(new JdbcConnectionService()) // перехватчик вызовов методов
        );
    }

    /**
     * Open new connection to database
     * @return connection
     */
    @AliveTime
    @Override
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
