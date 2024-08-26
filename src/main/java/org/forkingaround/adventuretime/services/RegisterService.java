package org.forkingaround.adventuretime.services;

import java.util.Set;
import java.util.HashSet;


import org.forkingaround.adventuretime.dtos.UserDto;
import org.forkingaround.adventuretime.facades.EncoderFacade;
import org.forkingaround.adventuretime.implementations.IEncryptFacade;
import org.forkingaround.adventuretime.models.Role;
import org.forkingaround.adventuretime.models.User;
import org.forkingaround.adventuretime.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
    public class RegisterService {
    
        UserRepository repository;
        RoleService roleService;
        IEncryptFacade encoderFacade;
    
        public RegisterService(UserRepository repository, RoleService roleService, EncoderFacade encoderFacade) {
            this.repository = repository;
            this.roleService = roleService;
            this.encoderFacade = encoderFacade;
        }
    
        public String save(UserDto newUserDto) {
    
            String passwordDecoded = encoderFacade.decode("base64", newUserDto.getPassword());
            String passwordEncoded = encoderFacade.encode("bcrypt",  passwordDecoded);
    
            User user = new User(newUserDto.getUsername(), passwordEncoded);
            user.setRoles(assignDefaultRole());
    
            repository.save(user);
    
            return "Success";
        }
    
        public Set<Role> assignDefaultRole() {
            Role defaultRole = roleService.getById(1L);
    
            Set<Role> roles = new HashSet<>();
            roles.add(defaultRole);
    
            return roles;
        }
    
    }

    
