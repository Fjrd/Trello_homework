package org.levelup.trello.service.jdbc;

import lombok.SneakyThrows;
import org.levelup.trello.jdbc.ConnectionService;
import org.levelup.trello.jdbc.JdbcConnectionService;
import org.levelup.trello.model.User;
import org.levelup.trello.service.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Класс, ответсвенный за работу с таблица пользователей (user, user_credentials)
 */

// @RequiredArgsConstructor
// public JdbcUserService(JdbcConnectionService service) { this.jdbcConnectionService = service; }

public class JdbcUserRepository implements UserRepository {

    // final - означает, что поле является константным - его нельзя изменить после присвоения значения
    // присвоение значения обязано происходить сразу же (либо в конструкторе)
    private final ConnectionService jdbcConnectionService;

    public JdbcUserRepository() {
//        this.jdbcConnectionService = new JdbcConnectionService();
        this.jdbcConnectionService = JdbcConnectionService.buildJdbcConnectionService();
    }

    // conn.createStatement("into users (login, name, email) values (" + login + ", " + name + " + password + ")")
    // "into users (login, name, email) values (" + login + ", " + name + ", " + password + ")"

    @Override
    public User createUser(String login, String email, String name, String password) {
        try (Connection connection = jdbcConnectionService.openConnection()) {

            String sql = "insert into users ( login, name, email) values ( ?, ?, ?)";
            // String sql = "insert into users (login, name, email) values (?, ?, ?) returning id";
            // PreparedStatement
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, login);
            stmt.setString(2, name);
            stmt.setString(3, email);

            // boolean hasResultSet = stmt.execute(); - выполяет запрос и возвращает вам таблицу в ответе (если она есть)
            int rowsAffected = stmt.executeUpdate(); // количество строк, которые были изменены вашим запросом
            System.out.println("Количество строк, которое было изменено: " + rowsAffected);

            // if (hasResultSet) {
            //      ResultSet keys = stmt.getResultSet();
            //      keys.next();
            //      int generatedId = keys.getInt(1);
            // }
            ResultSet keys = stmt.getGeneratedKeys(); // набор сгенерированных ID
            keys.next();

            int generateId = keys.getInt(1); // получить ID
            System.out.println("ID пользователя: " + generateId);

            saveUserCredentials(connection, generateId, password);

            return new User(generateId, name, login, email);

        } catch (SQLException exc) {
            System.out.println("Ошибка при работе с базой: " + exc.getMessage());
            throw new RuntimeException(exc);
        }
    }

    @Override
    public User findUserByLogin(String login) {
        return null;
    }

    @Override
    public List<User> findUsersByName(String name) {
        return null;
    }

    @Override
    public List<User> findUsersByIds(List<Integer> userIds) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @SneakyThrows
    private void saveUserCredentials(Connection connection, Integer userId, String password) {
        PreparedStatement stmt = connection.prepareStatement("insert into user_credentials values (?, ?)");
        stmt.setInt(1, userId);
        stmt.setString(2, password);

        stmt.executeUpdate();
    }

}
