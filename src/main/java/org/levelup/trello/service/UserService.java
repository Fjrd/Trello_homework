package org.levelup.trello.service;

import org.levelup.trello.model.User;

import java.util.ArrayList;

public interface UserService {
    User createUser(String name, String login, String email, String password);
    ArrayList<User> showAllUsers();
    boolean signIn(String login, String password);
}
