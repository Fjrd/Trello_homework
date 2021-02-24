package org.levelup.trello;

import org.levelup.trello.service.UserService;
import org.levelup.trello.service.jdbc.JdbcUserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TrelloApplication {

    public static void main(String[] args) throws IOException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("log");
        String login = consoleReader.readLine();
        System.out.println("email");
        String email = consoleReader.readLine();
        System.out.println("name");
        String name = consoleReader.readLine();
        System.out.println("pass");
        String password = consoleReader.readLine();

        UserService userService = new JdbcUserService();

        userService.createUser(login,email,name, password);
    }
}
