package org.levelup.reflection;

import org.levelup.trello.model.User;

public class ReflectionApp {
    public static void main(String[] args) {
        User user = new User(1, "test user", "tuser", "t@user.com");

        Class<?> userClass = user.getClass(); // ? - any type
        Class<?> userClassLiteral = User.class;
    }
}
