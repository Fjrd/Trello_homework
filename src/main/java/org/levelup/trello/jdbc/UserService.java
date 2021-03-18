package org.levelup.trello.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserService {

    public void printUsers() {
        JdbcConnectionService connectionService = new JdbcConnectionService();

        try (Connection connection = connectionService.openConnection()) {

            // Statement - нужен для выполнения SQL запроса
            Statement stmt = connection.createStatement();

            // для insert/update/delete используется executeUpdate();
            ResultSet rs = stmt.executeQuery("select * from users"); // <-- executeQuery используется для выполнения SELECT запросов

            // ResultSet - курсор, который указывает на конкретную строку (и он внутри содержит саму таблицу)
            while (rs.next()) {
                Integer id = rs.getInt(1); // id
                String login = rs.getString(2);
                String email = rs.getString("email");
                String password = rs.getString("password");
                String name = rs.getString(5);

                // String.join("delimiter", String...) -> String.join(":", 1, 2, 3, 4) -> "1:2:3:4"
                System.out.println(String.join(" | ", id.toString(), login, email, password, name));
            }


        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }

    }

}
