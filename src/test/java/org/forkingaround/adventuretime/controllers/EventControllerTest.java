package org.forkingaround.adventuretime.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.forkingaround.adventuretime.dtos.EventDto;
import org.forkingaround.adventuretime.exceptions.EventException;
import org.forkingaround.adventuretime.exceptions.EventNotFoundException;
import org.forkingaround.adventuretime.models.Event;
import org.forkingaround.adventuretime.services.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class EventControllerTest {

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    private EventDto eventDto;
    private Event event;

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
        event.setParticipants(Arrays.asList());
    }

    @Test
    void testGetAllEvents() {
        List<EventDto> eventList = Arrays.asList(eventDto);
        when(eventService.getAllEvents()).thenReturn(eventList);

        ResponseEntity<List<EventDto>> response = eventController.getAllEvents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(eventList, response.getBody());
        verify(eventService).getAllEvents();
    }

    @Test
    void testGetFeaturedEvents() {
        List<EventDto> featuredEventList = Arrays.asList(eventDto);
        when(eventService.getFeaturedEvents()).thenReturn(featuredEventList);

        ResponseEntity<List<EventDto>> response = eventController.getFeaturedEvents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(featuredEventList, response.getBody());
        verify(eventService).getFeaturedEvents();
    }

    @Test
    void testGetEventByIdSuccess() {
        when(eventService.getEventById(1L)).thenReturn(Optional.of(eventDto));

        ResponseEntity<EventDto> response = eventController.getEventById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(eventDto, response.getBody());
        verify(eventService).getEventById(1L);
    }

    @Test
    void testGetEventByIdNotFound() {
        when(eventService.getEventById(2L)).thenReturn(Optional.empty());

        ResponseEntity<EventDto> response = eventController.getEventById(2L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Event with id 2 not found", response.getBody());
        verify(eventService).getEventById(2L);
    }

    @Test
    void testAddEventSuccess() {
        when(eventService.addEvent(eventDto)).thenReturn(event);

        ResponseEntity<String> response = eventController.addEvent(eventDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Event added successfully with id: 1", response.getBody());
        verify(eventService).addEvent(eventDto);
    }

    @Test
    void testAddEventFailure() {
        when(eventService.addEvent(eventDto)).thenThrow(new EventException("Invalid event data"));

        ResponseEntity<String> response = eventController.addEvent(eventDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid event data: Invalid event data", response.getBody());
        verify(eventService).addEvent(eventDto);
    }

    @Test
    void testUpdateEventSuccess() {
        when(eventService.updateEvent(1L, eventDto)).thenReturn(event);

        ResponseEntity<String> response = eventController.updateEvent(1L, eventDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Event updated successfully", response.getBody());
        verify(eventService).updateEvent(1L, eventDto);
    }

    @Test
    void testUpdateEventNotFound() {
        doThrow(new EventNotFoundException("Event with id 1 not found")).when(eventService).updateEvent(1L, eventDto);

        ResponseEntity<String> response = eventController.updateEvent(1L, eventDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Event with id 1 not found: Event with id 1 not found", response.getBody());
        verify(eventService).updateEvent(1L, eventDto);
    }

    @Test
    void testUpdateEventFailure() {
        doThrow(new EventException("Invalid event data")).when(eventService).updateEvent(1L, eventDto);

        ResponseEntity<String> response = eventController.updateEvent(1L, eventDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid event data: Invalid event data", response.getBody());
        verify(eventService).updateEvent(1L, eventDto);
    }

    @Test
    void testDeleteEventSuccess() {
        doNothing().when(eventService).deleteEvent(1L);

        ResponseEntity<String> response = eventController.deleteEvent(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Event deleted successfully", response.getBody());
        verify(eventService).deleteEvent(1L);
    }

    @Test
    void testDeleteEventNotFound() {
        doThrow(new EventNotFoundException("Event with id 11 not found")).when(eventService).deleteEvent(11L);

        ResponseEntity<String> response = eventController.deleteEvent(11L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Event with id 11 not found: Event with id 11 not found", response.getBody());
        verify(eventService).deleteEvent(11L);
    }

    @Test
    void testDeleteEventFailure() {
        doThrow(new EventException("An error occurred while deleting the event")).when(eventService).deleteEvent(12L);

        ResponseEntity<String> response = eventController.deleteEvent(12L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred while deleting the event: An error occurred while deleting the event", response.getBody());
        verify(eventService).deleteEvent(12L);
    }
}
