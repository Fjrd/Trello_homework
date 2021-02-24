package org.levelup.trello;

import org.levelup.trello.service.UserService;
import org.levelup.trello.service.jdbc.JdbcUserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TrelloApplication {

    public static void main(String[] args) throws IOException {
        signUp();
    }

    public static void signUp(){
        System.out.println("SignUp:");
        String name, login, email, password;
        UserService userService = new JdbcUserService();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("Insert your name:");
            name = br.readLine();

            System.out.println("Insert your login:");
            login = br.readLine();

            System.out.println("Insert your email:");
            email = br.readLine();

            System.out.println("Insert your password:");
            password = br.readLine();

            userService.createUser(name, login, email, password);

        } catch (IOException e) {
            System.out.println("some IO exception");
            e.printStackTrace();
        }


    }
}
