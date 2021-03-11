package org.levelup.trello.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.levelup.trello.model.User;

import java.util.Properties;

public class HibernateUtils {

    private static SessionFactory factory = buildSessionFactory();

    private HibernateUtils() {}

    private static SessionFactory buildSessionFactory() {
        // SessionFactory - главный интерфейс Hibernate
        Properties hibernateProperties = new Properties();

        hibernateProperties.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/trello");
        hibernateProperties.setProperty("hibernate.connection.username", "postgres");
        hibernateProperties.setProperty("hibernate.connection.password", "root");
        // oracle.jdbc.OracleDriver
        hibernateProperties.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");

        hibernateProperties.setProperty("hibernate.show_sql", "true");
        hibernateProperties.setProperty("hibernate.format_sql", "true");

        // validate
        // update
        // create
        // create-drop
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "validate");

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(hibernateProperties)
                .build();

        Configuration configuration = new Configuration();
        return configuration
                .addAnnotatedClass(User.class)
                .buildSessionFactory(registry);
    }

    public static SessionFactory getFactory() {
        return factory;
    }

}
