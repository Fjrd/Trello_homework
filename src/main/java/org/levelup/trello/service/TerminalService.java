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
            case "boards" :
       /*   case "boards list" : printUserBoards();
            case "add board" : addNewBoard();
            case "delete board" : deleteBoard();*/
        }
    }

    @SneakyThrows
    private void signIn() {
        System.out.println("Insert your login:");
        String login = br.readLine();
        System.out.println("Insert your password:");
        String password = br.readLine();
        /*Integer userId = userService.signIn(login, password);
        if (userId != 0)
            userMenu(userId);*/
        user = userService.signIn(login, password);
        if (user != null)
            menu("User menu:", userMenu);
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

            userService.createUser(name, login, email, password);

        } catch (IOException e) {
            System.out.println("Wrong value, try again.");
            signUp();
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
}
