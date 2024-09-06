package org.forkingaround.adventuretime.repositories;

import java.util.List;

import org.forkingaround.adventuretime.models.Event;
import org.forkingaround.adventuretime.models.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByIsFeaturedTrue();

    @Query("SELECT p FROM Event e JOIN e.participants p WHERE e.id = :eventId")
    List<Participant> getParticipantsByEventId(@Param("eventId") Long eventId);
}
