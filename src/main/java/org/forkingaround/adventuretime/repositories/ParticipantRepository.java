package org.forkingaround.adventuretime.repositories;

import org.forkingaround.adventuretime.models.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
