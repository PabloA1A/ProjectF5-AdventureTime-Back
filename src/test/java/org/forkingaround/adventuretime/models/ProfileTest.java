package org.forkingaround.adventuretime.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileTest {

    private Profile profile;
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        profile = new Profile("test@example.com", user);
    }

    @Test
    public void testGettersAndSetters() {
        profile.setEmail("newemail@example.com");
        profile.setUser(user);

        assertEquals("newemail@example.com", profile.getEmail());
        assertEquals(user, profile.getUser());
    }

    @Test
    public void testConstructor() {
        assertEquals("test@example.com", profile.getEmail());
        assertEquals(user, profile.getUser());
    }

    @Test
    public void testNoArgsConstructor() {
        Profile profile = new Profile();
        assertNull(profile.getEmail());
        assertNull(profile.getUser());
    }

    @Test
    public void testAllArgsConstructor() {
        Profile profile = new Profile(1L, "test@example.com", user);
        assertEquals(1L, profile.getId());
        assertEquals("test@example.com", profile.getEmail());
        assertEquals(user, profile.getUser());
    }
    
    @Test
    public void testBuilder() {
        Profile profile = Profile.builder()
            .email("builder@example.com")
            .user(user)
            .build();

        assertEquals("builder@example.com", profile.getEmail());
        assertEquals(user, profile.getUser());
    }
}
