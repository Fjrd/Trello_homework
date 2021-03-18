package org.levelup.trello.service.hibernate;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.levelup.trello.model.User;
import org.levelup.trello.service.UserRepository;

import java.util.List;

@RequiredArgsConstructor // public HibernateUserRepository(SessionFactory factory) { this.factory = factory; }
public class HibernateUserRepository implements UserRepository {

    private final SessionFactory factory;

    @Override
    public User createUser(String login, String email, String name, String password) {
        try (Session session = factory.openSession()) { // открываем новое соединение к базе
            // ACID
            // A - атомарность
            // C - consistency - консистентность данных - согласованность данных
            // I - isolation - уровень изолированности транзакций - как ведут себя транзакции при одновременном обращении к одним и тем же данным
            // D - durability - доступность данных

            // commit - фиксация изменений
            // rollback - откат изменений

            Transaction tx = session.beginTransaction(); // начинаем транзакцию

            User user = new User(null, name, login, email); // new User(name, login, email);
            session.persist(user); // добавление пользователя в табличку users -> insert into users () values (....)

            tx.commit(); // фиксируем наши изменения

            return user;
        }
    }

    @Override
    public User findUserByLogin(String login) {
        try (Session session = factory.openSession()) {
            List<User> users = session.createQuery("from User where login = :login", User.class)
                    .setParameter("login", login)
                    // .uniqueResult()
                    // .getSingleResult()
                    .getResultList();

            return users.isEmpty() ? null : users.get(0);
        }
    }

    @Override
    public List<User> findUsersByName(String name) {
        try (Session session = factory.openSession()) {
            return session.createQuery("from User where name = :username", User.class)
                    .setParameter("username", name)
                    .getResultList();
        }
    }

    @Override
    public List<User> findUsersByIds(List<Integer> userIds) {
        try (Session session = factory.openSession()) {
            // session.createNativeQuery("select u.* from users u join boards b on u.id = b.owner_id where board_id = 1", User.class);
            return session.createQuery("from User where id in (:ids)", User.class)
                    .setParameter("ids", userIds)
                    .getResultList();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = factory.openSession()) {
            // createQuery - HQL - Hibernate Query Language
            return session.createQuery("from User", User.class) // select * from users
                    .getResultList(); // List<User>
        }
    }

    // get()
    // load()

    public User getUserById(Integer id) {
        try (Session session = factory.openSession()) {
            return session.get(User.class, id);
        }
    }

    public User loadUserById(Integer id) {
        try (Session session = factory.openSession()) {
            User user = session.load(User.class, id);
            System.out.println("User is loaded in repository");
            //user.getName(); // Хибернейт выполняет SQL запрос
            return user;
        }
    }

}
