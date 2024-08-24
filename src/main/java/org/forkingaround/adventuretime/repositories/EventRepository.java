package org.forkingaround.adventuretime.repositories;

import java.util.List;

import org.forkingaround.adventuretime.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByIsFeaturedTrue();
}