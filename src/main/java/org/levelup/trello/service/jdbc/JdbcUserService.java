package org.levelup.trello.service.jdbc;
import org.levelup.trello.jdbc.JdbcConnectionService;
import org.levelup.trello.model.User;
import org.levelup.trello.service.UserService;

import java.sql.*;

public class JdbcUserService implements UserService {

    private final JdbcConnectionService jdbcConnectionService;

    public JdbcUserService(){
        this.jdbcConnectionService = new JdbcConnectionService();
    }

    @Override
    public User createUser(String login, String email, String name, String password) {
        try (Connection connection = jdbcConnectionService.openConnection()){
            String sql = "insert into users (id, login, email, name) values (nextval('user_id_sequence')?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, login);
            stmt.setString(2, email);
            stmt.setString(3, name);

            int rowAffected = stmt.executeUpdate();
            System.out.println(rowAffected + " row affected");

            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            int generatedID = keys.getInt(1);
            System.out.println("User ID " + generatedID);

            saveUserCredentials(connection, generatedID, password);

            return new User(generatedID, login, email, name);

        } catch (SQLException exc) {
            System.out.println("Ошибка при работе с базой " + exc.getMessage());
            System.out.println();
            exc.printStackTrace();
            throw new RuntimeException(exc);

        }
    }



    private void saveUserCredentials(Connection connection, Integer userId, String password) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("insert into user_credentials valuee (?, ?)");
        stmt.setInt(1, userId);
        stmt.setString(2, password);
        stmt.executeUpdate();

    }

}
