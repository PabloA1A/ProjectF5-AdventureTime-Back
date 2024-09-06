package org.forkingaround.adventuretime.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Set;

import org.forkingaround.adventuretime.dtos.RegisterDto;
import org.forkingaround.adventuretime.facades.EncoderFacade;
import org.forkingaround.adventuretime.models.Role;
import org.forkingaround.adventuretime.models.User;
import org.forkingaround.adventuretime.models.Profile;
import org.forkingaround.adventuretime.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RegisterServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private ProfileService profileService;

    @Mock
    private EncoderFacade encoderFacade;

    @InjectMocks
    private RegisterService registerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
     
        RegisterDto registerDto = new RegisterDto("testuser", "encodedpassword", "testemail@example.com");
        String passwordDecoded = "decodedpassword";
        String passwordEncoded = "bcryptencodedpassword";

        Role defaultRole = new Role();
        defaultRole.setId(1L);

        User user = new User("testuser", passwordEncoded);
      

        Profile profile = new Profile("testemail@example.com", user);

        when(encoderFacade.decode("base64", "encodedpassword")).thenReturn(passwordDecoded);
        when(encoderFacade.encode("bcrypt", passwordDecoded)).thenReturn(passwordEncoded);
        when(roleService.getById(1L)).thenReturn(defaultRole);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setRoles(new HashSet<>(Set.of(defaultRole)));
            return savedUser;
        });
        when(profileService.save(any(Profile.class))).thenReturn(profile);

        
        User savedUser = registerService.save(registerDto);

        assertNotNull(savedUser, "Saved user should not be null");
        assertEquals("testuser", savedUser.getUsername(), "Username should match");
        assertEquals(passwordEncoded, savedUser.getPassword(), "Encoded password should match");
        assertNotNull(savedUser.getRoles(), "Roles set should not be null");
        assertTrue(savedUser.getRoles().contains(defaultRole), "User should have the default role");

        verify(encoderFacade).decode("base64", "encodedpassword");
        verify(encoderFacade).encode("bcrypt", passwordDecoded);
        verify(roleService).getById(1L);
        verify(userRepository).save(any(User.class));
        verify(profileService).save(any(Profile.class));
    }

    @Test
    public void testAssignDefaultRole() {
  
        Role defaultRole = new Role();
        defaultRole.setId(1L);

        when(roleService.getById(1L)).thenReturn(defaultRole);

       
        Set<Role> roles = registerService.assignDefaultRole();

    
        assertNotNull(roles, "Roles set should not be null");
        assertEquals(1, roles.size(), "Roles set should contain exactly one role");
        assertTrue(roles.contains(defaultRole), "Roles set should contain the default role");

        verify(roleService).getById(1L);
    }
}
