package org.forkingaround.adventuretime.controllers;

import org.forkingaround.adventuretime.dtos.EventDto;
import org.forkingaround.adventuretime.exceptions.EventException;
import org.forkingaround.adventuretime.exceptions.EventNotFoundException;
import org.forkingaround.adventuretime.models.Event;
import org.forkingaround.adventuretime.services.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EventControllerTest {

    @InjectMocks
    private EventController eventController;

    @Mock
    private EventService eventService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllEventsHome() {
        List<EventDto> eventDtoList = new ArrayList<>();
        Page<EventDto> page = new PageImpl<>(eventDtoList);
        Pageable pageable = Pageable.unpaged();

        when(eventService.getAllEventsHome(pageable)).thenReturn(page);

        ResponseEntity<Page<EventDto>> response = eventController.getAllEvents(pageable);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());
    }

    @Test
    public void testGetAllEvents() {
        List<EventDto> eventDtoList = new ArrayList<>();

        when(eventService.getAllEvents()).thenReturn(eventDtoList);

        ResponseEntity<List<EventDto>> response = eventController.getAllEvents();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(eventDtoList, response.getBody());
    }

    @Test
    public void testGetFeaturedEvents() {
        List<EventDto> featuredEvents = new ArrayList<>();

        when(eventService.getFeaturedEvents()).thenReturn(featuredEvents);

        ResponseEntity<List<EventDto>> response = eventController.getFeaturedEvents();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(featuredEvents, response.getBody());
    }

    @Test
    public void testAddEventSuccess() throws EventException {
        EventDto eventDto = new EventDto();
        Event event = new Event();
        event.setId(1L);

        when(eventService.addEvent(eventDto)).thenReturn(event);

        ResponseEntity<String> response = eventController.addEvent(eventDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Event added successfully with id: 1", response.getBody());
    }

    @Test
    public void testAddEventFailure() throws EventException {
        EventDto eventDto = new EventDto();
        when(eventService.addEvent(eventDto)).thenThrow(new EventException("Invalid data"));

        ResponseEntity<String> response = eventController.addEvent(eventDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid event data: Invalid data", response.getBody());
    }

    @Test
    public void testUpdateEventNotFound() throws EventNotFoundException, EventException {
        EventDto eventDto = new EventDto();
        Long eventId = 1L;
        doThrow(new EventNotFoundException("Event not found")).when(eventService).updateEvent(eventId, eventDto);

        ResponseEntity<String> response = eventController.updateEvent(eventId, eventDto);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Event not found: Event not found", response.getBody());
    }

    @Test
    public void testUpdateEventFailure() throws EventNotFoundException, EventException {
        EventDto eventDto = new EventDto();
        Long eventId = 1L;
        doThrow(new EventException("Invalid data")).when(eventService).updateEvent(eventId, eventDto);

        ResponseEntity<String> response = eventController.updateEvent(eventId, eventDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid event data: Invalid data", response.getBody());
    }

    @Test
    public void testDeleteEventSuccess() throws EventNotFoundException, EventException {
        Long eventId = 1L;
        doNothing().when(eventService).deleteEvent(eventId);

        ResponseEntity<String> response = eventController.deleteEvent(eventId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testDeleteEventNotFound() throws EventNotFoundException, EventException {
        Long eventId = 1L;
        doThrow(new EventNotFoundException("Event not found")).when(eventService).deleteEvent(eventId);

        ResponseEntity<String> response = eventController.deleteEvent(eventId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Event not found: Event not found", response.getBody());
    }

    @Test
    public void testDeleteEventFailure() throws EventNotFoundException, EventException {
        Long eventId = 1L;
        doThrow(new EventException("Error deleting event")).when(eventService).deleteEvent(eventId);

        ResponseEntity<String> response = eventController.deleteEvent(eventId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred while deleting the event: Error deleting event", response.getBody());
    }

    @Test
    public void testGetEventByIdFound() {
        Long eventId = 1L;
        EventDto eventDto = new EventDto();
        Optional<EventDto> optionalEventDto = Optional.of(eventDto);

        when(eventService.getEventById(eventId)).thenReturn(optionalEventDto);

        ResponseEntity<Optional<EventDto>> response = eventController.getEventById(eventId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(optionalEventDto, response.getBody());
    }

    @Test
    public void testGetEventByIdNotFound() {
        Long eventId = 1L;
        Optional<EventDto> optionalEventDto = Optional.empty();

        when(eventService.getEventById(eventId)).thenReturn(optionalEventDto);

        ResponseEntity<Optional<EventDto>> response = eventController.getEventById(eventId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(optionalEventDto, response.getBody());
    }
}
