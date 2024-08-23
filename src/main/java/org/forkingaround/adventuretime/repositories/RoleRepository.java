package org.forkingaround.adventuretime.repositories;

import org.forkingaround.adventuretime.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
    
}
