package org.levelup.trello.service;

import org.levelup.trello.model.Board;
import org.levelup.trello.model.User;

import java.util.ArrayList;

public interface UserService {
    User createUser(String name, String login, String email, String password);
    ArrayList<User> showAllUsers();
    User showUser(Integer id);
    Integer signIn(String login, String password);
    ArrayList<Board> showAllUserBoards(Integer userId);
    void deleteBoard(Integer id);
    Board editBoard(Integer id);
    Board addNewBoard(String name, Boolean favourite, Integer userId);


}
