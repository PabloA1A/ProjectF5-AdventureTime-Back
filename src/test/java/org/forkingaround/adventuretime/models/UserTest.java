package org.forkingaround.adventuretime.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User user;
    private Profile profile;
    private Role role;
    private Participant participant;

    @BeforeEach
    void setUp() {
        profile = new Profile();
        role = new Role();
        participant = new Participant();
        user = new User("username", "password", profile);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        Set<Participant> participants = new HashSet<>();
        participants.add(participant);
        user.setParticipants(participants);
    }

    @Test
    void testUserConstructor() {
        User userWithParams = new User("testUser", "testPass", profile);
        assertAll(
                () -> assertThat(userWithParams.getUsername(), is("testUser")),
                () -> assertThat(userWithParams.getPassword(), is("testPass")),
                () -> assertThat(userWithParams.getProfile(), is(profile)));
    }

}
