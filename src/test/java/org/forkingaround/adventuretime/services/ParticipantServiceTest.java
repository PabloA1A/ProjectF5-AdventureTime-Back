package org.forkingaround.adventuretime.services;


import org.forkingaround.adventuretime.dtos.ParticipantDto;
import org.forkingaround.adventuretime.exceptions.ParticipantNotFoundException;
import org.forkingaround.adventuretime.models.Event;
import org.forkingaround.adventuretime.models.Participant;
import org.forkingaround.adventuretime.models.User;
import org.forkingaround.adventuretime.repositories.EventRepository;
import org.forkingaround.adventuretime.repositories.ParticipantRepository;
import org.forkingaround.adventuretime.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParticipantServiceTest {

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ParticipantService participantService;

    @Test
    void addParticipant_ShouldReturnParticipantDto_WhenValidInput() {
        ParticipantDto participantDto = new ParticipantDto(null, LocalDateTime.now(), 1L, 1L);
        Event event = new Event();
        event.setId(1L);
        User user = new User();
        user.setId(1L);

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Participant participant = new Participant();
        participant.setId(1L);
        participant.setEvent(event);
        participant.setUser(user);
        when(participantRepository.save(any(Participant.class))).thenReturn(participant);

        ParticipantDto result = participantService.addParticipant(participantDto);

        assertEquals(1L, result.getEventId());
        assertEquals(1L, result.getUserId());
        verify(participantRepository, times(1)).save(any(Participant.class));
    }

    @Test
    void getParticipantById_ShouldThrowException_WhenParticipantNotFound() {
        when(participantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ParticipantNotFoundException.class, () -> participantService.getParticipantById(1L));
    }
}
