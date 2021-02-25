package org.levelup.trello.service;

import lombok.SneakyThrows;
import org.levelup.trello.model.Board;
import org.levelup.trello.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TerminalService {

    private UserService userService;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public TerminalService(UserService userService) {
        this.userService = userService;
    }

    @SneakyThrows
    public void startUp(){
        System.out.println("Welcome!");
        System.out.println("1 - log in");
        System.out.println("2 - sign up");
        System.out.println("3 - help");
        System.out.println("4 - exit");
        while (true){
            int command = Integer.parseInt(br.readLine());
            switch (command){
                case 1 : signIn();
                    break;
                case 2 : signUp();
                    break;
                case 3 : startUp();
                    break;
                case 4 : System.exit(0);
                    break;
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
            userMenu(userId);
    }

    public void signUp(){
        String name, login, email, password;

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

    @SneakyThrows
    private void userMenu(Integer id) {
        User user = userService.showUser(id);
        System.out.println("User found! Now you can manage your boards!");
        System.out.println("1 - Manage your boards");
        System.out.println("2 - Exit");
        while (true){
            int command = Integer.parseInt(br.readLine());
            switch (command){
                case 1 : manageBoards(user);
                    break;
                case 2 : startUp();
                    break;
            }
        }
    }

    @SneakyThrows
    private void manageBoards(User user) {
        System.out.println("1 - Show board list");
        System.out.println("2 - Add new board");
        System.out.println("3 - Edit board");
        System.out.println("4 - Delete board");
        while (true){
            int command = Integer.parseInt(br.readLine());
            switch (command){
                case 1 : printUserBoards(user);
                    break;
                case 2 : addNewBoard(user);
                    break;
                case 3 :
                    break;
                case 4 : deleteBoard(user);
                    break;
            }
        }
    }

    private void deleteBoard(User user) {
        System.out.println("which board do you want to remove?");

    }

    @SneakyThrows
    private void addNewBoard(User user) {
        System.out.println("New board:");
        String name = br.readLine();
        Boolean favourite = Boolean.valueOf(br.readLine());
        Integer userId = user.getId();
        userService.addNewBoard(name, favourite, userId);
    }

    private void printUserBoards(User user) {
        System.out.println("This is all your boards:");
        for (Board board : userService.showAllUserBoards(user.getId())) {
            System.out.println(board.toString());
        }

    }

    public void printAllUsers(){
        userService.showAllUsers().forEach(System.out::println);
    }


}
