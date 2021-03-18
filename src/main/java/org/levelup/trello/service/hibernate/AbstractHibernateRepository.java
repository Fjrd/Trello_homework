package org.levelup.trello.service.hibernate;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.function.Function;

@RequiredArgsConstructor
public abstract class AbstractHibernateRepository {

    protected final SessionFactory factory;

    protected <R> R runWithTransaction(Function<Session, R> function) {
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            // interface method invocation -> inter.method();

            R result = function.apply(session);

            tx.commit();
            return result;
        } catch (Exception exc) {
            System.out.println("Error occurred during query execution: " + exc.getMessage());
            if (tx != null) {
                tx.rollback(); // откат транзакции
            }

            throw new RuntimeException("Couldn't process query", exc);
        }
    }

//    interface SessionMethod<T> {
//        T m(Session session);
//    }

    // interface TxMethod { void method(); }
    // runWithTransaction(TxMethod obj) {
    //      ...
    //      obj.method();
    //      ...
    //  }

    // runWithTransaction(new TxMethod() {
    //      @Override
    //      public void method() {
    //           .....
    //      }
    // });

    // runWithTransaction(() -> {
    //      ...
    // });

}
