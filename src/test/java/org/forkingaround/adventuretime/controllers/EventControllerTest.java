package org.forkingaround.adventuretime.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.forkingaround.adventuretime.dtos.EventDto;
import org.forkingaround.adventuretime.dtos.NewEventDto;
import org.forkingaround.adventuretime.exceptions.EventException;
import org.forkingaround.adventuretime.exceptions.EventNotFoundException;
import org.forkingaround.adventuretime.models.Event;
import org.forkingaround.adventuretime.services.EventService;
import org.forkingaround.adventuretime.services.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class EventControllerTest {

    @Mock
    private EventService eventService;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private EventController eventController;

    private NewEventDto newEventDto;
    private EventDto eventDto;
    private Event event;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        newEventDto = new NewEventDto();
        newEventDto.setTitle("Sample Event");
        newEventDto.setDescription("Sample Description");
        newEventDto.setImageHash(Optional.empty());
        newEventDto.setEventDateTime(LocalDateTime.now());

        eventDto = new EventDto(
                1L,
                newEventDto.getTitle(),
                newEventDto.getDescription(),
                Optional.empty(),
                newEventDto.getEventDateTime(),
                newEventDto.getMaxParticipants(),
                newEventDto.getIsAvailable(),
                newEventDto.getIsFeatured(),
                0,
                null);

        event = new Event();
        event.setId(1L);
    }

    @Test
    public void testAddEventSuccess() throws EventException {
        when(eventService.addEvent(any(EventDto.class))).thenReturn(event);

        ResponseEntity<String> response = eventController.addEvent(newEventDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Event added successfully with id: 1", response.getBody());
    }

    @Test
    public void testUpdateEventSuccess() throws EventException, EventNotFoundException {
        ResponseEntity<String> response = eventController.updateEvent(1L, newEventDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Event updated successfully", response.getBody());
    }

    @Test
    public void testDeleteEventSuccess() throws EventNotFoundException, EventException {
        ResponseEntity<String> response = eventController.deleteEvent(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteEventNotFound() throws EventNotFoundException, EventException {
        doThrow(new EventNotFoundException("Event not found")).when(eventService).deleteEvent(1L);

        ResponseEntity<String> response = eventController.deleteEvent(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Event not found: Event not found", response.getBody());
    }

    @Test
    public void testDeleteEventFailure() throws EventNotFoundException, EventException {
        doThrow(new EventException("Error deleting event")).when(eventService).deleteEvent(1L);

        ResponseEntity<String> response = eventController.deleteEvent(1L);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred while deleting the event: Error deleting event", response.getBody());
    }

    @Test
    public void testGetAllEvents() {
        when(eventService.getAllEvents()).thenReturn(Collections.singletonList(eventDto));

        ResponseEntity<List<EventDto>> response = eventController.getAllEvents();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetFeaturedEvents() {
        when(eventService.getFeaturedEvents()).thenReturn(Collections.singletonList(eventDto));

        ResponseEntity<List<EventDto>> response = eventController.getFeaturedEvents();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetEventByIdSuccess() {
        when(eventService.getEventById(1L)).thenReturn(Optional.of(eventDto));

        ResponseEntity<Optional<EventDto>> response = eventController.getEventById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(eventDto, response.getBody().get());
    }

    @Test
    public void testGetEventByIdNotFound() {
        when(eventService.getEventById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Optional<EventDto>> response = eventController.getEventById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(Optional.empty(), response.getBody());
    }
}
