package org.forkingaround.adventuretime.services;

import org.forkingaround.adventuretime.dtos.EventDto;
import org.forkingaround.adventuretime.exceptions.EventException;
import org.forkingaround.adventuretime.exceptions.EventNotFoundException;
import org.forkingaround.adventuretime.models.Event;
import org.forkingaround.adventuretime.repositories.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEventByIdNotFound() {
        when(eventRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<EventDto> result = eventService.getEventById(1L);
        assertFalse(result.isPresent());
    }

    @Test
    void testAddEventWithNullTitle() {
        EventDto eventDto = new EventDto();
        eventDto.setTitle(null);

        Exception exception = assertThrows(EventException.class, () -> {
            eventService.addEvent(eventDto);
        });
        assertEquals("Event title cannot be null or empty", exception.getMessage());
    }

    @Test
    void testUpdateEventNotFound() {
        EventDto eventDto = new EventDto();
        when(eventRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(EventNotFoundException.class, () -> {
            eventService.updateEvent(1L, eventDto);
        });
        assertEquals("Event with id 1 not found", exception.getMessage());
    }

    @Test
    void testDeleteEvent() {
        Event event = new Event();
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));

        eventService.deleteEvent(1L);
        verify(eventRepository, times(1)).delete(event);
    }

    @Test
    void testDeleteEventNotFound() {
        when(eventRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(EventNotFoundException.class, () -> {
            eventService.deleteEvent(1L);
        });
        assertEquals("Event with id 1 not found", exception.getMessage());
    }
}
