package org.levelup.trello.service;

import org.levelup.trello.model.Board;
import org.levelup.trello.model.User;

import java.util.ArrayList;

public interface UserService {
    User addNewUser(String name, String login, String email, String password);
    ArrayList<User> showAllUsers();
    User showUserById(Integer id);
    User verifyUser(String login, String password);

    //TODO move to BoardService
    ArrayList<Board> showUserBoards(Integer userId);
    void deleteBoard(Integer id);
    Board editBoard(Integer id);
    Board addNewBoard(String name, Boolean favourite, Integer userId);


}
