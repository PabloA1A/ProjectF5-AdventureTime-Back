package org.forkingaround.adventuretime.controllers;

import java.util.HashMap;
import java.util.Map;

import org.forkingaround.adventuretime.dtos.UserDto;
import org.forkingaround.adventuretime.models.User;
import org.forkingaround.adventuretime.services.RegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "${api-endpoint}/register")
public class RegisterController {

    private final RegisterService service;

    public RegisterController(RegisterService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody UserDto newUser) {
        User user = service.save(newUser);

        Map<String, String> json = new HashMap<>();
        json.put("message", "Register successful");
        json.put("username", user.getUsername());

        String email = user.getProfile() != null ? user.getProfile().getEmail() : "No email found";
        json.put("email", email);

        return ResponseEntity.status(HttpStatus.CREATED).body(json);
    }
}
