package org.forkingaround.adventuretime.services;



import org.forkingaround.adventuretime.exceptions.RoleNotFoundException;
import org.forkingaround.adventuretime.models.Role;
import org.forkingaround.adventuretime.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    
    RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public Role getById(Long id) {
        Role role = repository.findById(id).orElseThrow(() -> new RoleNotFoundException("Role not found"));
        return role;
    }

}