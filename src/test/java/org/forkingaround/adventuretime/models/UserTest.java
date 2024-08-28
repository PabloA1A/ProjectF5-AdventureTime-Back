package org.forkingaround.adventuretime.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
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

    @Test
    void testUserBuilder() {
        User userWithBuilder = User.builder()
                .username("builderUser")
                .password("builderPass")
                .profile(profile)
                .build();
        assertAll(
                () -> assertThat(userWithBuilder.getUsername(), is("builderUser")),
                () -> assertThat(userWithBuilder.getPassword(), is("builderPass")),
                () -> assertThat(userWithBuilder.getProfile(), is(profile)));
    }

    @Test
    void testGettersAndSetters() {
        assertAll(
                () -> assertThat(user.getUsername(), is("username")),
                () -> assertThat(user.getPassword(), is("password")),
                () -> assertThat(user.getProfile(), is(profile)),
                () -> assertThat(user.getRoles(), hasItem(role)),
                () -> assertThat(user.getParticipants(), hasItem(participant)));

        user.setUsername("newUsername");
        user.setPassword("newPassword");
        Profile newProfile = new Profile(); // Crear un nuevo objeto de perfil
        user.setProfile(newProfile);

        Set<Role> newRoles = new HashSet<>();
        user.setRoles(newRoles);

        Set<Participant> newParticipants = new HashSet<>();
        user.setParticipants(newParticipants);

        assertAll(
                () -> assertThat(user.getUsername(), is("newUsername")),
                () -> assertThat(user.getPassword(), is("newPassword")),
                () -> assertThat(user.getProfile(), is(newProfile)),
                () -> assertThat(user.getRoles(), is(empty())),
                () -> assertThat(user.getParticipants(), is(empty())));
    }
}
