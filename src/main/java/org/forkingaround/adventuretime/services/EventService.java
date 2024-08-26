package org.forkingaround.adventuretime.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.forkingaround.adventuretime.dtos.EventDto;
import org.forkingaround.adventuretime.exceptions.EventException;
import org.forkingaround.adventuretime.exceptions.EventNotFoundException;
import org.forkingaround.adventuretime.models.Event;
import org.forkingaround.adventuretime.repositories.EventRepository;

@Service
@RequiredArgsConstructor  
public class EventService {

    private final EventRepository eventRepository;

    public List<EventDto> getAllEvents() {
        return eventRepository.findAll().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    private EventDto convertToDto(Event event) {
        return new EventDto(
            event.getId(),
            event.getTitle(),
            event.getDescription(),
            event.getImageUrl(),
            event.getEventDateTime(),
            event.getMaxParticipants(),
            event.getIsAvailable(),
            event.getIsFeatured(),
            event.getParticipants().size() 
        );
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

    public Event addEvent(EventDto eventDto) {
        if (eventDto.getTitle() == null || eventDto.getTitle().isEmpty()) {
            throw new EventException("Event title cannot be null or empty");
        }

        Event newEvent = new Event();
        newEvent.setTitle(eventDto.getTitle());
        newEvent.setDescription(eventDto.getDescription());
        newEvent.setEventDateTime(eventDto.getEventDateTime());
        newEvent.setIsFeatured(eventDto.getIsFeatured());

        return eventRepository.save(newEvent);
    }

    public Event updateEvent(Long id, EventDto eventDto) {
        Event event = eventRepository.findById(id)
            .orElseThrow(() -> new EventNotFoundException("Event with id " + id + " not found"));

        event.setTitle(eventDto.getTitle());
        event.setDescription(eventDto.getDescription());
        event.setEventDateTime(eventDto.getEventDateTime());
        event.setIsFeatured(eventDto.getIsFeatured());

        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
            .orElseThrow(() -> new EventNotFoundException("Event with id " + id + " not found"));

        eventRepository.delete(event);
    }
}
