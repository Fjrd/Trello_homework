package org.levelup.trello.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class HibernateUtils {

    private HibernateUtils() {}

    private static SessionFactory buildSessionFactory(){
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/trello");
        hibernateProperties.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        hibernateProperties.setProperty("show_sql", "true");
        hibernateProperties.setProperty("format_sql", "true");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "validate");

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(hibernateProperties)
                .build();

        Configuration configuration = new Configuration();
        return configuration
                .buildSessionFactory(registry);
    }
}
