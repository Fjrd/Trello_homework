package org.levelup.trello.hibernate;

import org.hibernate.SessionFactory;

public class App {

    public static void main(String[] args) {
        SessionFactory factory = HibernateUtils.getFactory();

        factory.close();
    }

}
