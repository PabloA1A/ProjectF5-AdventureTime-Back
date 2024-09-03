package org.forkingaround.adventuretime.controllers;

import org.forkingaround.adventuretime.dtos.EventDto;
import org.forkingaround.adventuretime.dtos.ParticipantDto;
import org.forkingaround.adventuretime.exceptions.ParticipantNotFoundException;
import org.forkingaround.adventuretime.services.EmailService;
import org.forkingaround.adventuretime.services.EventService;
import org.forkingaround.adventuretime.services.ParticipantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParticipantControllerTest {

    @Mock
    private ParticipantService participantService;

    @Mock
    private EventService eventService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ParticipantController participantController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllParticipants_Success() {
        List<ParticipantDto> participants = Arrays.asList(new ParticipantDto(null, null, null, null), new ParticipantDto(null, null, null, null));
        when(participantService.getAllParticipants()).thenReturn(participants);

        ResponseEntity<List<ParticipantDto>> response = participantController.getAllParticipants();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(participants, response.getBody());
    }

    @Test
    void getAllParticipants_Exception() {
        when(participantService.getAllParticipants()).thenThrow(new RuntimeException());

        ResponseEntity<List<ParticipantDto>> response = participantController.getAllParticipants();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getParticipantById_Success() throws ParticipantNotFoundException {
        Long id = 1L;
        ParticipantDto participantDto = new ParticipantDto(id, null, id, id);
        when(participantService.getParticipantById(id)).thenReturn(participantDto);

        ResponseEntity<ParticipantDto> response = participantController.getParticipantById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(participantDto, response.getBody());
    }

    @Test
    void getParticipantById_NotFound() throws ParticipantNotFoundException {
        Long id = 1L;
        when(participantService.getParticipantById(id)).thenThrow(new ParticipantNotFoundException("Not found"));

        ResponseEntity<ParticipantDto> response = participantController.getParticipantById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    // @Test
    // void joinEvent_Success() {
    //     Long eventId = 1L;
    //     Long userId = 1L;

    //     when(participantService.joinEvent(eventId, userId)).thenReturn(true);

    //     EventDto eventDto = new EventDto(userId, null, null, null, null, 0, null, null, 0, null);
    //     eventDto.setTitle("Sample Event");
    //     when(eventService.getEventById(eventId)).thenReturn(Optional.of(eventDto));

    //     ResponseEntity<String> response = participantController.joinEvent(eventId, userId);

    //     assertEquals(HttpStatus.OK, response.getStatusCode());
    //     assertEquals("Joined successfully", response.getBody());
    // }

    @Test
    void joinEvent_EventFull() {
        Long eventId = 1L;
        Long userId = 1L;
        when(participantService.joinEvent(eventId, userId)).thenReturn(false);
        EventDto eventDto = new EventDto(userId, null, null, null, null, 0, null, null, 0, null);
        eventDto.setMaxParticipants(10);
        eventDto.setParticipantsCount(10);
        when(eventService.getEventById(eventId)).thenReturn(Optional.of(eventDto));

        ResponseEntity<String> response = participantController.joinEvent(eventId, userId);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("Event is full", response.getBody());
    }

    @Test
    void joinEvent_AlreadyRegistered() {
        Long eventId = 1L;
        Long userId = 1L;
        when(participantService.joinEvent(eventId, userId)).thenReturn(false);
        EventDto eventDto = new EventDto(userId, null, null, null, null, 0, null, null, 0, null);
        eventDto.setMaxParticipants(10);
        eventDto.setParticipantsCount(5);
        when(eventService.getEventById(eventId)).thenReturn(Optional.of(eventDto));

        ResponseEntity<String> response = participantController.joinEvent(eventId, userId);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        assertEquals("User is already registered for the event", response.getBody());
    }

    @Test
    void deleteParticipant_Success() throws ParticipantNotFoundException {
        Long eventId = 1L;
        Long participantId = 1L;
        doNothing().when(participantService).deleteParticipant(eventId, participantId);

        ResponseEntity<String> response = participantController.deleteParticipant(eventId, participantId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Participant deleted successfully", response.getBody());
    }

    @Test
    void deleteParticipant_NotFound() throws ParticipantNotFoundException {
        Long eventId = 1L;
        Long participantId = 1L;
        doThrow(new ParticipantNotFoundException("Not found")).when(participantService).deleteParticipant(eventId, participantId);

        ResponseEntity<String> response = participantController.deleteParticipant(eventId, participantId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Participant not found: Not found", response.getBody());
    }

    @Test
    void unregisterFromEvent_Success() throws ParticipantNotFoundException {
        Long eventId = 1L;
        Long userId = 1L;
        doNothing().when(participantService).unregisterFromEvent(eventId, userId);

        ResponseEntity<String> response = participantController.unregisterFromEvent(eventId, userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully unregistered from the event", response.getBody());
    }

    @Test
    void unregisterFromEvent_NotFound() throws ParticipantNotFoundException {
        Long eventId = 1L;
        Long userId = 1L;
        doThrow(new ParticipantNotFoundException("Not found")).when(participantService).unregisterFromEvent(eventId, userId);

        ResponseEntity<String> response = participantController.unregisterFromEvent(eventId, userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Participant not found: Not found", response.getBody());
    }
}