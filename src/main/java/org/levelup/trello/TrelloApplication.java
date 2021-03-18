package org.levelup.trello;

import org.hibernate.SessionFactory;
import org.levelup.trello.hibernate.HibernateUtils;
import org.levelup.trello.model.User;
import org.levelup.trello.service.hibernate.HibernateUserRepository;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("ALL")
public class TrelloApplication {

    // SOLID
    public static void main(String[] args) throws IOException {

        SessionFactory factory = HibernateUtils.getFactory();

//        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
//
//        System.out.println("Введите логин:");
//        String login = consoleReader.readLine();
//
//        System.out.println("Введите имя:");
//        String name = consoleReader.readLine();
//
//        System.out.println("Введите email:");
//        String email = consoleReader.readLine();
//
//        System.out.println("Введите пароль:");
//        String password = consoleReader.readLine();
//        UserRepository userRepository = new HibernateUserRepository(factory);
//
//        User user = userRepository.createUser(login, email, name, password);
//        System.out.println(user);

        HibernateUserRepository hibernateUserRepository = new HibernateUserRepository(factory);

        List<User> users = hibernateUserRepository.findUsersByName("Dmitry");
        users.forEach(user -> System.out.println(user));

        System.out.println();

        User getUser = hibernateUserRepository.getUserById(510);
        System.out.println(getUser);

        System.out.println();
        User loadUser = hibernateUserRepository.loadUserById(5);
        System.out.println("User is loaded");
        System.out.println(loadUser);

        factory.close();

    }

}
