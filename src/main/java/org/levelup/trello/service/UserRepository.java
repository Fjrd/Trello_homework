package org.levelup.trello.service;

import org.levelup.trello.model.User;

import java.util.List;

public interface UserRepository {

    User createUser(String login, String email, String name, String password);

    User findUserByLogin(String login);

    List<User> findUsersByName(String name);

    List<User> findUsersByIds(List<Integer> userIds);

    List<User> getAllUsers();

}
