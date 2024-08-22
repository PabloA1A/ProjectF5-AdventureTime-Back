package org.forkingaround.adventuretime.repositories;

import java.util.Optional;

import org.forkingaround.adventuretime.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByUsername(String username);

}
