package org.levelup.trello;

import org.hibernate.SessionFactory;
import org.levelup.trello.hibernate.HibernateUtils;

public class TrelloHibernateApp {
    public static void main(String[] args){
        SessionFactory factory = HibernateUtils.getFactory();
        //buildSessionFactory();
        factory.close();
    }
}
