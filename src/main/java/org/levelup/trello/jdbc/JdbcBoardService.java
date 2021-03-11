package org.levelup.trello.jdbc;

import lombok.SneakyThrows;
import org.levelup.trello.model.Board;
import org.levelup.trello.service.BoardService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class JdbcBoardService implements BoardService {

    private final JdbcConnectionService jdbcConnectionService;
    private final Connection connection;

    @SneakyThrows
    public JdbcBoardService() {
        jdbcConnectionService = new JdbcConnectionService();
        connection = jdbcConnectionService.openConnection();
    }

    @SneakyThrows
    @Override
    public ArrayList<Board> showUserBoards(Integer userId) {
        ArrayList<Board> boardList = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("select * from boards where owner_id = ?");
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Integer id = rs.getInt(1);
            String name = rs.getString(2);
            Boolean favourite = rs.getBoolean(3);
            Integer ownerId = rs.getInt(4);
            Board board = new Board(id, name, favourite, ownerId);
            boardList.add(board);
        }
        return boardList;
    }


    @SneakyThrows
    @Override
    public Board addNewBoard(String name, Boolean favourite, Integer userId) {
        PreparedStatement ps = connection.prepareStatement("insert into boards (name, favourite, owner_id) values (?,?,?)");
        ps.setString(1, name);
        ps.setBoolean(2, favourite);
        ps.setInt(3, userId);
        ps.executeUpdate();
        return null;
    }

    @Override
    public Board editBoard(Integer id) {
        return null;
    }



    @SneakyThrows
    @Override
    public void deleteBoard(Integer id) {
        PreparedStatement ps = connection.prepareStatement("delete from boards where id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}
