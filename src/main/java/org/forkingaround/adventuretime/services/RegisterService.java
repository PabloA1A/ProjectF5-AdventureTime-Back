package org.forkingaround.adventuretime.services;

import java.util.Set;
import java.util.HashSet;

import org.forkingaround.adventuretime.dtos.RegisterDto;
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

    public User save(RegisterDto newRegisterDto) {
        String passwordDecoded = encoderFacade.decode("base64", newRegisterDto.getPassword());
        String passwordEncoded = encoderFacade.encode("bcrypt", passwordDecoded);

        User user = new User(newRegisterDto.getUsername(), passwordEncoded);
        user.setRoles(assignDefaultRole());

        User savedUser = userRepository.save(user);

        Profile profile = new Profile(newRegisterDto.getEmail(), savedUser);
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
