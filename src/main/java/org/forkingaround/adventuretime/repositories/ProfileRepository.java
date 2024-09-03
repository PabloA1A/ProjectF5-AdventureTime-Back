package org.forkingaround.adventuretime.repositories;

import java.util.Optional;

import org.forkingaround.adventuretime.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUserId(Long userId);
}