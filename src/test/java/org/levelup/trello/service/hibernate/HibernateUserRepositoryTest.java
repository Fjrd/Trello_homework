package org.levelup.trello.service.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.levelup.trello.model.User;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

public class HibernateUserRepositoryTest {

    private SessionFactory factory;
    private Session session;
    private Transaction transaction;

    private HibernateUserRepository userRepository;

    // BeforeEach/BeforeAll
    // AfterEach/AfterAll

    // BeforeAll
    // BeforeEach
    // Test
    // AfterEach
    // BeforeEach
    // Test
    // AfterEach
    // AfterAll

    // BeforeAll/AfterAll -> public static void ...

    @BeforeEach
    public void setupRepository() {
        // factory = new SessionFactoryStub();
        factory = Mockito.mock(SessionFactory.class);
        session = Mockito.mock(Session.class);
        transaction = Mockito.mock(Transaction.class);

        Mockito.when(factory.openSession()).thenReturn(session);
        Mockito.when(session.beginTransaction()).thenReturn(transaction);

        userRepository = new HibernateUserRepository(factory);
    }

    @Test
    public void testCreateUser_whenAllDataIsValid_thenPersistUser() {
        // when
        User user = userRepository.createUser("login", "email", "name", "password");

        Assertions.assertEquals("login", user.getLogin());
        Assertions.assertEquals("email", user.getEmail());
        Assertions.assertEquals("name", user.getName());

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(session).persist(userArgumentCaptor.capture());
        Mockito.verify(transaction).commit();

        Assertions.assertSame(user, userArgumentCaptor.getValue());
    }

    @Test
    public void testFindUserByLogin_whenUserExists_thenReturnUser() {
        // given
        String login = "login";
        User expectedResult = new User(null, null, login, null);
        List<User> list = Collections.singletonList(expectedResult);

        Query query = Mockito.mock(Query.class);

        Mockito.when(session.createQuery("from User where login = :login", User.class))
                .thenReturn(query);
        Mockito.when(query.setParameter(ArgumentMatchers.anyString(), ArgumentMatchers.eq(login)))
                .thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(list);

        // when
        User result = userRepository.findUserByLogin(login);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void testFindUserByLogin_whenUserDoesNotExist_thenReturnNull() {

    }

}