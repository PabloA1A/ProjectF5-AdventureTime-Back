package org.forkingaround.adventuretime.controllers;



import org.forkingaround.adventuretime.dtos.UserDto;
import org.forkingaround.adventuretime.services.RegisterService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "${api-endpoint}/register")
public class RegisterController {

    RegisterService service;

    public RegisterController(RegisterService service) {
        this.service = service;
    }

    @PostMapping
    public String register(@RequestBody UserDto newUser) {
        System.out.println(newUser.getUsername());
        return service.save(newUser);
    }
}

