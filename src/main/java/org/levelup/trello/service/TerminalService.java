package org.levelup.trello.service;

import lombok.SneakyThrows;
import org.levelup.trello.model.Board;
import org.levelup.trello.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class TerminalService {

    List<String> mainMenu = List.of("sign in", "sign up", "exit");
    List<String> userMenu = List.of("boards", "back to start", "exit");
    List<String> boardsMenu = List.of("boards list", "add board" /*, "edit board"*/, "delete board", "back to start", "exit");
    //List<String> allCommands = Stream.of(mainMenu, userMenu, boardsMenu).flatMap(Collection::stream).collect(Collectors.toList());
    private UserService userService;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    User user;

    public TerminalService(UserService userService) {
        this.userService = userService;
    }

    public void startUp(){
        menu("Main menu. Please log in or Sign in:", mainMenu);
    }

    @SneakyThrows
    public String menu(String title, List<String> list) {
        System.out.println(title);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i+1 + " - " + list.get(i));
        }
        System.out.println();
        while (true) {
            //TODO handle java.lang.ArrayIndexOutOfBoundsException
            Integer num = Integer.parseInt(br.readLine());
            String command = list.get(num-1);
            if (command != null){
                System.out.println(command);
                chooseCommand(command);
            }

        }
    }

    public void chooseCommand(String command){
        switch (command){
            case "sign in" : signIn();
            case "sign up" : signUp();
            case "back to start" : startUp();
            case "exit": System.exit(0);
            case "boards" : menu("Boards manager", boardsMenu);
            case "boards list" : {
                printUserBoards();
                menu("Boards manager", boardsMenu);
            }
            case "add board" : {
                addNewBoard();
                printUserBoards();
                menu("Boards manager", boardsMenu);
            }
            case "delete board" : {
                deleteBoard();
                menu("Boards manager", boardsMenu);
            }
        }
    }

    @SneakyThrows
    private void signIn() {
        System.out.println("Insert your login:");
        String login = br.readLine();
        System.out.println("Insert your password:");
        String password = br.readLine();
        user = userService.verifyUser(login, password);
        if (user != null) {
            System.out.println("Welcome " + user.getName() + "!");
            menu("User menu:", userMenu);
        }
        else{
            System.out.println("Wrong login or password, try again.");
            signIn();
        }
    }

    public void signUp(){

        try {
            System.out.println("Insert your name:");
            String name = br.readLine();

            System.out.println("Insert your login:");
            String login = br.readLine();

            System.out.println("Insert your email:");
            String email = br.readLine();

            System.out.println("Insert your password:");
            String password = br.readLine();

            userService.addNewUser(name, login, email, password);

        } catch (IOException e) {
            System.out.println("Wrong value, try again.");
            signUp();
        }
        System.out.println("User created successfully");
        signIn();
    }

    private void printUserBoards() {
        System.out.println("Your boards list:");
        for (Board board : userService.showUserBoards(user.getId())) {
            System.out.println(board.toString());
        }
    }

    @SneakyThrows
    private void addNewBoard() {
        System.out.println("Board name:");
        String name = br.readLine();
        System.out.println("Is this your favourite board? Type \"true\" or \"false\"");
        Boolean favourite = Boolean.valueOf(br.readLine());
        userService.addNewBoard(name, favourite, user.getId());
    }

    @SneakyThrows
    private void deleteBoard() {
        List boards = userService.showUserBoards(user.getId());
        for (int i = 0; i < boards.size(); i++) {
            System.out.println(i+1 + " - " + boards.get(i).toString());
        }
        System.out.println("which board do you want to remove?");
        userService.deleteBoard(Integer.parseInt(br.readLine()));
        printUserBoards();

    }
}
