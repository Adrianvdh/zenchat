package com.zenchat.server.api.user.repository;

import com.zenchat.server.api.AbstractIntegrationTest;
import com.zenchat.server.api.user.model.User;
import com.zenchat.server.repository.Repositories;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static com.zenchat.server.api.user.UserConstants.PASSWORD;
import static com.zenchat.server.api.user.UserConstants.USERNAME;

public class UserRepositoryTest extends AbstractIntegrationTest {

    private UserRepository userRepository = Repositories.getRepository(UserRepository.class);

    @Before
    public void setUp() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    public void testSaveUser() {
        User givenUser = new User(UUID.randomUUID().toString(), USERNAME, PASSWORD);

        userRepository.save(givenUser);

        User foundUser = userRepository.findByUsername(USERNAME);
        Assert.assertEquals(givenUser, foundUser);
    }
}
