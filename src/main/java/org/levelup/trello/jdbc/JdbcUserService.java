package org.levelup.trello.jdbc;
import lombok.SneakyThrows;
import org.levelup.trello.model.User;
import org.levelup.trello.service.UserService;

import java.sql.*;
import java.util.ArrayList;

public class JdbcUserService implements UserService {

    private final JdbcConnectionService jdbcConnectionService;
    private final Connection connection;

    @SneakyThrows
    public JdbcUserService(){
        this.jdbcConnectionService = new JdbcConnectionService();
        connection = jdbcConnectionService.openConnection();
    }

    @Override
    public User addNewUser(String name, String login, String email, String password) {

        try (Connection connection = jdbcConnectionService.openConnection()){

            String sql = "insert into users (name, login, email) values (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
            stmt.setString(2, login);
            stmt.setString(3, email);
            stmt.executeUpdate();


            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            int generatedUserId = keys.getInt(1);

            saveUserCredentials(connection, generatedUserId, password);

            return new User(generatedUserId, login, email, name);

        }
        catch (SQLIntegrityConstraintViolationException e){
            System.out.println("User with this login already exists");
        }
        catch (SQLException e) {
            System.out.println("Database exception");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ArrayList<User> showAllUsers(){
        ArrayList<User> userList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from users");
            while(rs.next()){
                Integer id = rs.getInt(1);
                String name = rs.getString(2);
                String login = rs.getString(3);
                String email = rs.getString(4);

                userList.add(new User(id, name, login, email));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return userList;
    }

    @SneakyThrows
    @Override
    public User showUserById(Integer id) {
        PreparedStatement ps = connection.prepareStatement("select * from users where id = ?");
        ps.setString(1, String.valueOf(id));
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            String name = rs.getString(2);
            String login = rs.getString(3);
            String email = rs.getString(4);
            return new User(id, name, login, email);
        }
        else
            return null;
    }

    @SneakyThrows
    private void saveUserCredentials(Connection connection, Integer userId, String password){
        PreparedStatement stmt = connection.prepareStatement("insert into user_credentials values (?, ?)");
        stmt.setInt(1, userId);
        stmt.setString(2, password);
        stmt.executeUpdate();
    }

    @SneakyThrows
    public User verifyUser(String login, String password){
       PreparedStatement ps = connection.prepareStatement("SELECT id, name, login, email " +
               "from user_credentials a " +
               "inner join users b " +
               "on a.user_id = b.id " +
               "where login = ? and password = ?");
       ps.setString(1, login);
       ps.setString(2, password);
       ResultSet rs = ps.executeQuery();
       if (rs.next() == true)
           return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
       else
           return null;
    }


}
