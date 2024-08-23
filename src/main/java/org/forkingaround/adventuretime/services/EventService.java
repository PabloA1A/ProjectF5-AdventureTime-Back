package org.forkingaround.adventuretime.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.forkingaround.adventuretime.exceptions.EventNotFoundException;
import org.forkingaround.adventuretime.models.Event;
import org.forkingaround.adventuretime.repositories.EventRepository;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    
    public List<Event> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        if (events.isEmpty()) {
            throw new EventNotFoundException("No events found");
        }
        return events;
    }

    public List<Event> getFeaturedEvents() {
        List<Event> featuredEvents = eventRepository.findByIsFeaturedTrue();
        if (featuredEvents.isEmpty()) {
            throw new EventNotFoundException("No featured events found");
        }
        return featuredEvents;
    }


}
