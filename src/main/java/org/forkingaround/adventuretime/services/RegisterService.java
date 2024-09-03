package org.forkingaround.adventuretime.services;

import java.util.Set;
import java.util.HashSet;

import org.forkingaround.adventuretime.dtos.UserDto;
import org.forkingaround.adventuretime.facades.EncoderFacade;
import org.forkingaround.adventuretime.implementations.IEncryptFacade;
import org.forkingaround.adventuretime.models.Role;
import org.forkingaround.adventuretime.models.User;
import org.forkingaround.adventuretime.models.Profile;
import org.forkingaround.adventuretime.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ProfileService profileService;
    private final IEncryptFacade encoderFacade;

    public RegisterService(UserRepository userRepository, RoleService roleService, ProfileService profileService, EncoderFacade encoderFacade) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.profileService = profileService;
        this.encoderFacade = encoderFacade;
    }

    public User save(UserDto newUserDto) {
        // Decodificar y codificar la contraseña
        String passwordDecoded = encoderFacade.decode("base64", newUserDto.getPassword());
        String passwordEncoded = encoderFacade.encode("bcrypt", passwordDecoded);

        // Crear el usuario sin email (el email se maneja en Profile)
        User user = new User(newUserDto.getUsername(), passwordEncoded);
        user.setRoles(assignDefaultRole());

        // Guardar el usuario en la base de datos
        User savedUser = userRepository.save(user);

        // Crear y asociar el perfil con el usuario
        Profile profile = new Profile(newUserDto.getEmail(), savedUser);
        profileService.save(profile);

        return savedUser;
    }

    public Set<Role> assignDefaultRole() {
        Role defaultRole = roleService.getById(1L);

        Set<Role> roles = new HashSet<>();
        roles.add(defaultRole);

        return roles;
    }
}
