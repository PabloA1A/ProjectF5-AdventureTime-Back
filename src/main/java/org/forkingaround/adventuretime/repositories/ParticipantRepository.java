package org.forkingaround.adventuretime.repositories;

import java.util.Optional;

import org.forkingaround.adventuretime.models.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Optional<Participant> findByEventIdAndUserId(Long eventId, Long userId);
}
