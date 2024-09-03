package org.forkingaround.adventuretime.dtos;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ParticipantDtoTest {

    @Test
    void testConstructorAndGetters() {
        Long id = 1L;
        LocalDateTime joinedAt = LocalDateTime.now();
        Long eventId = 100L;
        Long userId = 200L;

        ParticipantDto participant = new ParticipantDto(id, joinedAt, eventId, userId);

        assertEquals(id, participant.getId());
        assertEquals(joinedAt, participant.getJoinedAt());
        assertEquals(eventId, participant.getEventId());
        assertEquals(userId, participant.getUserId());
    }

    @Test
    void testSetters() {
        ParticipantDto participant = new ParticipantDto(null, null, null, null);

        Long id = 2L;
        LocalDateTime joinedAt = LocalDateTime.now();
        Long eventId = 101L;
        Long userId = 201L;

        participant.setId(id);
        participant.setJoinedAt(joinedAt);
        participant.setEventId(eventId);
        participant.setUserId(userId);

        assertEquals(id, participant.getId());
        assertEquals(joinedAt, participant.getJoinedAt());
        assertEquals(eventId, participant.getEventId());
        assertEquals(userId, participant.getUserId());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();
        ParticipantDto participant1 = new ParticipantDto(1L, now, 100L, 200L);
        ParticipantDto participant2 = new ParticipantDto(1L, now, 100L, 200L);
        ParticipantDto participant3 = new ParticipantDto(2L, now, 100L, 200L);

        assertEquals(participant1, participant2);
        assertNotEquals(participant1, participant3);
        assertEquals(participant1.hashCode(), participant2.hashCode());
        assertNotEquals(participant1.hashCode(), participant3.hashCode());
    }

    @Test
    void testToString() {
        LocalDateTime now = LocalDateTime.now();
        ParticipantDto participant = new ParticipantDto(1L, now, 100L, 200L);
        String toString = participant.toString();

        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("joinedAt=" + now.toString()));
        assertTrue(toString.contains("eventId=100"));
        assertTrue(toString.contains("userId=200"));
    }
}