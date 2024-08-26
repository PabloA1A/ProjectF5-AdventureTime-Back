package org.forkingaround.adventuretime.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.forkingaround.adventuretime.dtos.EventRequest;
import org.forkingaround.adventuretime.exceptions.EventException;
import org.forkingaround.adventuretime.exceptions.EventNotFoundException;
import org.forkingaround.adventuretime.models.Event;
import org.forkingaround.adventuretime.models.Participant;
import org.forkingaround.adventuretime.repositories.EventRepository;

@Service
@RequiredArgsConstructor  
public class EventService {

    private final EventRepository eventRepository;

    public List<Event> getAllEvents() {
        List<Event> events = eventRepository.findAll();
      
        for (Event event : events) {
           List<Participant> participants = eventRepository.getParticipantsByEventId(event.getId());
           event.setParticipantsCount(participants.size());
        }
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

    public Optional<Event> getEventById(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) {
            throw new EventNotFoundException("Event with id " + id + " not found");
        }
        return event;
    }

    public Event addEvent(EventRequest eventRequest) {
        if (eventRequest.getTitle() == null || eventRequest.getTitle().isEmpty()) {
            throw new EventException("Event title cannot be null or empty");
        }

        Event newEvent = new Event();
        newEvent.setTitle(eventRequest.getTitle());
        newEvent.setDescription(eventRequest.getDescription());
        newEvent.setEventDateTime(eventRequest.getEventDateTime());
        newEvent.setIsFeatured(eventRequest.getIsFeatured());

        return eventRepository.save(newEvent);
    }

    public Event updateEvent(Long id, EventRequest eventRequest) {
        Event event = eventRepository.findById(id)
            .orElseThrow(() -> new EventNotFoundException("Event with id " + id + " not found"));

        event.setTitle(eventRequest.getTitle());
        event.setDescription(eventRequest.getDescription());
        event.setEventDateTime(eventRequest.getEventDateTime());
        event.setIsFeatured(eventRequest.getIsFeatured());

        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
            .orElseThrow(() -> new EventNotFoundException("Event with id " + id + " not found"));

        eventRepository.delete(event);
    }
}
