package org.levelup.trello.service.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.levelup.trello.model.Board;
import org.levelup.trello.model.BoardColumn;
import org.levelup.trello.model.Team;
import org.levelup.trello.model.User;
import org.levelup.trello.model.UserCredentials;

import java.util.Properties;

public class H2HibernateUtils {

    private static SessionFactory factory = buildSessionFactory();

    private H2HibernateUtils() {}

    private static SessionFactory buildSessionFactory() {
        // SessionFactory - главный интерфейс Hibernate
        Properties hibernateProperties = new Properties();

        hibernateProperties.setProperty("hibernate.connection.url", "jdbc:h2:mem:trello;INIT=create schema if not exists trello");
        hibernateProperties.setProperty("hibernate.connection.username", "sa");
        hibernateProperties.setProperty("hibernate.connection.password", "");
        // oracle.jdbc.OracleDriver
        hibernateProperties.setProperty("hibernate.connection.driver_class", "org.h2.Driver");

        hibernateProperties.setProperty("hibernate.show_sql", "true");
        hibernateProperties.setProperty("hibernate.format_sql", "true");

        // validate
        // update
        // create
        // create-drop
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(hibernateProperties)
                .build();

        Configuration configuration = new Configuration();
        return configuration
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(UserCredentials.class)
                .addAnnotatedClass(Board.class)
                .addAnnotatedClass(Team.class)
                .addAnnotatedClass(BoardColumn.class)
                .buildSessionFactory(registry);
    }

    public static SessionFactory getFactory() {
        return factory;
    }


}
