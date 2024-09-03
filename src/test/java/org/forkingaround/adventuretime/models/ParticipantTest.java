package org.forkingaround.adventuretime.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ParticipantTest {

    @Test
    void testParticipantCreation() {
        Event event = new Event(); 
        User user = new User(); 
        LocalDateTime joinedAt = LocalDateTime.now();

        Participant participant = Participant.builder()
                .id(1L)
                .joinedAt(joinedAt)
                .event(event)
                .user(user)
                .build();

        assertNotNull(participant);
        assertEquals(1L, participant.getId());
        assertEquals(joinedAt, participant.getJoinedAt());
        assertEquals(event, participant.getEvent());
        assertEquals(user, participant.getUser());
    }
}