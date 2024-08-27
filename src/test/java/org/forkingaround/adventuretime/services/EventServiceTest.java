package org.forkingaround.adventuretime.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.forkingaround.adventuretime.dtos.EventDto;
import org.forkingaround.adventuretime.exceptions.EventNotFoundException;
import org.forkingaround.adventuretime.models.Event;
import org.forkingaround.adventuretime.repositories.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    private Event event;
    private EventDto eventDto;

    @BeforeEach
    void setUp() {
        eventDto = new EventDto(
            1L,
            "Sample Event",
            "This is a sample event.",
            "http://example.com/sample.jpg",
            LocalDateTime.now(),
            100,
            true,
            false,
            50
        );

        event = new Event();
        event.setId(1L);
        event.setTitle("Sample Event");
        event.setDescription("This is a sample event.");
        event.setImageUrl("http://example.com/sample.jpg");
        event.setEventDateTime(LocalDateTime.now());
        event.setMaxParticipants(100);
        event.setIsAvailable(true);
        event.setIsFeatured(false);
        event.setParticipants(Arrays.asList()); // Mocked participants
    }

    @Test
    void testGetAllEvents() {
        when(eventRepository.findAll()).thenReturn(Arrays.asList(event));

        List<EventDto> events = eventService.getAllEvents();

        assertEquals(1, events.size());
        assertEquals(eventDto.getId(), events.get(0).getId());
        assertEquals(eventDto.getTitle(), events.get(0).getTitle());
        verify(eventRepository).findAll();
    }

    @Test
    void testGetFeaturedEvents() {
        when(eventRepository.findByIsFeaturedTrue()).thenReturn(Arrays.asList(event));

        List<EventDto> featuredEvents = eventService.getFeaturedEvents();

        assertEquals(1, featuredEvents.size());
        assertEquals(eventDto.getId(), featuredEvents.get(0).getId());
        assertEquals(eventDto.getTitle(), featuredEvents.get(0).getTitle());
        verify(eventRepository).findByIsFeaturedTrue();
    }

    @Test
    void testGetEventByIdSuccess() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        Optional<EventDto> retrievedEvent = eventService.getEventById(1L);

        assertEquals(true, retrievedEvent.isPresent());
        assertEquals(eventDto.getId(), retrievedEvent.get().getId());
        assertEquals(eventDto.getTitle(), retrievedEvent.get().getTitle());
        verify(eventRepository).findById(1L);
    }

    @Test
    void testGetEventByIdNotFound() {
        when(eventRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<EventDto> retrievedEvent = eventService.getEventById(2L);

        assertEquals(Optional.empty(), retrievedEvent);
        verify(eventRepository).findById(2L);
    }

    @Test
    void testAddEventSuccess() {
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event addedEvent = eventService.addEvent(eventDto);

        assertEquals(event.getId(), addedEvent.getId());
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void testAddEventFailure() {
        EventDto invalidEventDto = new EventDto(
            1L,
            "",  // Invalid title
            "Description",
            "http://example.com/image.jpg",
            LocalDateTime.now(),
            50,
            true,
            false,
            10
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            eventService.addEvent(invalidEventDto);
        });

        assertEquals("Event title cannot be null or empty", exception.getMessage());
    }

    @Test
    void testUpdateEventSuccess() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event updatedEvent = eventService.updateEvent(1L, eventDto);

        assertEquals(event.getId(), updatedEvent.getId());
        verify(eventRepository).findById(1L);
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void testUpdateEventNotFound() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        EventNotFoundException exception = assertThrows(EventNotFoundException.class, () -> {
            eventService.updateEvent(1L, eventDto);
        });

        assertEquals("Event with id 1 not found", exception.getMessage());
    }

    @Test
    void testDeleteEventSuccess() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        doNothing().when(eventRepository).delete(any(Event.class));

        eventService.deleteEvent(1L);

        verify(eventRepository).findById(1L);
        verify(eventRepository).delete(any(Event.class));
    }

    @Test
    void testDeleteEventNotFound() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        EventNotFoundException exception = assertThrows(EventNotFoundException.class, () -> {
            eventService.deleteEvent(1L);
        });

        assertEquals("Event with id 1 not found", exception.getMessage());
    }
}
