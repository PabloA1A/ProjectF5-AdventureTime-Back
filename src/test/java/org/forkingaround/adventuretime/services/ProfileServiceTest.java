package org.forkingaround.adventuretime.services;

import org.forkingaround.adventuretime.models.Profile;
import org.forkingaround.adventuretime.repositories.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileService profileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        
        Profile profile = new Profile();
        profile.setEmail("test@example.com");

        when(profileRepository.save(profile)).thenReturn(profile);
        Profile result = profileService.save(profile);

        assertEquals(profile, result);
    }

    @Test
    void testGetEmailByUserIdWhenProfileExists() {
        
        Long userId = 1L;
        Profile profile = new Profile();
        profile.setEmail("test@example.com");

      
        when(profileRepository.findById(userId)).thenReturn(Optional.of(profile));
        String email = profileService.getEmailByUserId(userId);

     
        assertEquals("test@example.com", email);
    }

    @Test
    void testGetEmailByUserIdWhenProfileDoesNotExist() {
    
        Long userId = 1L;

 
        when(profileRepository.findById(userId)).thenReturn(Optional.empty());
        String email = profileService.getEmailByUserId(userId);

   
        assertNull(email);
    }
}
