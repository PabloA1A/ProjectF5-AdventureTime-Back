package org.forkingaround.adventuretime.services;

import org.forkingaround.adventuretime.exceptions.RoleNotFoundException;
import org.forkingaround.adventuretime.models.Role;
import org.forkingaround.adventuretime.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoleServiceTest {

    @Mock
    private RoleRepository repository;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetById_Success() {
     
        Long roleId = 1L;
        Role mockRole = new Role();
        mockRole.setId(roleId);
        when(repository.findById(roleId)).thenReturn(Optional.of(mockRole));

      
        Role result = roleService.getById(roleId);

    
        assertNotNull(result);
        assertEquals(roleId, result.getId());
        verify(repository, times(1)).findById(roleId);
    }

    @Test
    public void testGetById_RoleNotFound() {
       
        Long roleId = 1L;
        when(repository.findById(roleId)).thenReturn(Optional.empty());

    
        RoleNotFoundException thrown = assertThrows(RoleNotFoundException.class, () -> {
            roleService.getById(roleId);
        });
        assertEquals("Role not found", thrown.getMessage());
        verify(repository, times(1)).findById(roleId);
    }
}
