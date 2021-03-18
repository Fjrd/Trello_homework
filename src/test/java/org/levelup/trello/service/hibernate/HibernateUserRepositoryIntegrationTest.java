package org.levelup.trello.service.hibernate;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.levelup.trello.model.User;

public class HibernateUserRepositoryIntegrationTest {

    private final SessionFactory factory = H2HibernateUtils.getFactory();
    private final HibernateUserRepository userRepository = new HibernateUserRepository(factory);

    @Test
    public void testCreateUser_whenDataIsValid_thenCreateUser() {
        // given
        String login = "log";
        String name = "name";
        String email = "name@email.com";

        // when
        User user = userRepository.createUser(login, email, name, null);

        // then
        Assertions.assertNotNull(user.getId());

        User foundByLogin = userRepository.findUserByLogin(login);
        Assertions.assertEquals(foundByLogin.getId(), user.getId());
    }

}
