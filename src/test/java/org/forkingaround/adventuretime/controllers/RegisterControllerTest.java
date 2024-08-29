package org.forkingaround.adventuretime.controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Map;

import org.forkingaround.adventuretime.dtos.UserDto;
import org.forkingaround.adventuretime.models.User;
import org.forkingaround.adventuretime.services.RegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RegisterControllerTest {

    @Mock
    private RegisterService service;

    @InjectMocks
    private RegisterController controller;

    private UserDto userDto;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicialización de datos de prueba
        userDto = new UserDto("username", "password");
        user = new User("username", "password");
    }

    @Test
    @SuppressWarnings("null")
    void testRegister() {
        when(service.save(userDto)).thenReturn(user);

        ResponseEntity<Map<String, String>> response = controller.register(userDto);

        assertThat(response.getStatusCode(), is(HttpStatus.ACCEPTED));

        Map<String, String> responseBody = response.getBody();
        assertThat(responseBody, is(notNullValue()));
        assertThat(responseBody.get("message"), is("Register"));
        assertThat(responseBody.get("username"), is("username"));

        verify(service).save(userDto);
    }
}
