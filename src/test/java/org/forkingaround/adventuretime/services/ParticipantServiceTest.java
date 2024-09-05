package org.forkingaround.adventuretime.services;

import org.forkingaround.adventuretime.dtos.ParticipantDto;
import org.forkingaround.adventuretime.exceptions.ParticipantNotFoundException;
import org.forkingaround.adventuretime.models.Event;
import org.forkingaround.adventuretime.models.Participant;
import org.forkingaround.adventuretime.models.User;
import org.forkingaround.adventuretime.repositories.EventRepository;
import org.forkingaround.adventuretime.repositories.ParticipantRepository;
import org.forkingaround.adventuretime.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ParticipantServiceTest {

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ParticipantService participantService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddParticipant() {
        ParticipantDto participantDto = new ParticipantDto(1L, LocalDateTime.now(), 1L, 1L);

        Event event = new Event();
        event.setId(1L);
        User user = new User();
        user.setId(1L);

        Participant participant = new Participant();
        participant.setId(1L);
        participant.setJoinedAt(LocalDateTime.now());
        participant.setEvent(event);
        participant.setUser(user);

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(participantRepository.save(any(Participant.class))).thenReturn(participant);

        ParticipantDto result = participantService.addParticipant(participantDto);

        assertNotNull(result);
        assertEquals(1L, result.getEventId());
        assertEquals(1L, result.getUserId());
    }

    @Test
    public void testGetAllParticipants() {
        Participant participant = new Participant();
        participant.setId(1L);
        participant.setJoinedAt(LocalDateTime.now());
        participant.setEvent(new Event());
        participant.setUser(new User());

        when(participantRepository.findAll()).thenReturn(List.of(participant));

        List<ParticipantDto> result = participantService.getAllParticipants();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testGetParticipantById() {
        Participant participant = new Participant();
        participant.setId(1L);
        participant.setJoinedAt(LocalDateTime.now());
        participant.setEvent(new Event());
        participant.setUser(new User());

        when(participantRepository.findById(1L)).thenReturn(Optional.of(participant));

        ParticipantDto result = participantService.getParticipantById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testGetParticipantByIdNotFound() {
        when(participantRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ParticipantNotFoundException.class, () -> {
            participantService.getParticipantById(1L);
        });

        assertEquals("Participant not found with ID: 1", exception.getMessage());
    }

    @Test
    public void testJoinEvent() {
        Event event = new Event();
        event.setId(1L);
        event.setParticipantsCount(0);
        event.setMaxParticipants(5);

        User user = new User();
        user.setId(1L);

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(participantRepository.findByEventIdAndUserId(1L, 1L)).thenReturn(Optional.empty());

        boolean result = participantService.joinEvent(1L, 1L);

        assertTrue(result);
    }

    @Test
    public void testJoinEventAlreadyRegistered() {
        when(participantRepository.findByEventIdAndUserId(1L, 1L)).thenReturn(Optional.of(new Participant()));

        boolean result = participantService.joinEvent(1L, 1L);

        assertFalse(result);
    }

    @Test
    public void testDeleteParticipant() {
        Participant participant = new Participant();
        participant.setId(1L);
        Event event = new Event();
        event.setId(1L);
        participant.setEvent(event);

        when(participantRepository.findById(1L)).thenReturn(Optional.of(participant));
        when(eventRepository.existsById(1L)).thenReturn(true);

        participantService.deleteParticipant(1L, 1L);

        verify(participantRepository).delete(participant);
    }

}
