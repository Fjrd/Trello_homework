package org.levelup.trello.service.jdbc;

import lombok.SneakyThrows;
import org.levelup.trello.model.User;
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
                    break;
                }
            }
        }
    }

    @SneakyThrows
    private void signIn() {
        String login;
        String password;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println();
        System.out.println("Insert your login:");
        login = br.readLine();
        System.out.println("Insert your password:");
        password = br.readLine();
        Integer userId = userService.signIn(login, password);
        if (userId != 0)
            System.out.println("User found! Now you can manage you boards!");
            boardList(userId);
    }

    private void boardList(Integer id) {
        User user = userService.showUser(id);
        System.out.println("board list:");

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

        } catch (IOException e) {
            System.out.println("Wrong value, try again");
            signUp();
        }
    }
}
