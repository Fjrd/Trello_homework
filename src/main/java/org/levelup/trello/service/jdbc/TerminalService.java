package org.levelup.trello.service.jdbc;

import lombok.SneakyThrows;
import org.levelup.trello.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TerminalService {

    private UserService userService;

    public TerminalService(UserService userService) {
        this.userService = userService;
    }

    @SneakyThrows
    public void startUp(){
        System.out.println("Welcome stranger!");
        System.out.println("If you are registered user, please sign in. Type \"log in\" or \"sign up\"");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            String command = br.readLine();
            switch (command){
                case ("sign up"):{
                    signUp();
                    break;
                }
                case("log in"):{
                    signIn();
                }
            }
        }
    }

    @SneakyThrows
    private void signIn() {
        String login;
        String password;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("login:");
        login = br.readLine();
        System.out.println("password:");
        password = br.readLine();
        if (userService.signIn(login, password))
            System.out.println("User found! Now you can manage you boards!");
    }

    public void printAllUsers(){
        userService.showAllUsers().forEach(System.out::println);
    }

    public void signUp(){
        String name, login, email, password;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("Create new user:");

            System.out.println("Insert your name:");
            name = br.readLine();

            System.out.println("Insert your login:");
            login = br.readLine();

            System.out.println("Insert your email:");
            email = br.readLine();

            System.out.println("Insert your password:");
            password = br.readLine();

            userService.createUser(name, login, email, password);
            System.out.println("User creation success");

        } catch (IOException e) {
            System.out.println("Wrong value, try again");
            signUp();
        }
    }
}
