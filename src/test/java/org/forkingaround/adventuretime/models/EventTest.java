package org.forkingaround.adventuretime.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTest {

    private Event event;
    private Participant participant1;
    private Participant participant2;

    @BeforeEach
    void setUp() {
        // Create Participants
        participant1 = new Participant();
        participant1.setJoinedAt(LocalDateTime.now());
        
        participant2 = new Participant();
        participant2.setJoinedAt(LocalDateTime.now().plusHours(1));

        // Create Event with Participants
        event = Event.builder()
                .title("Sample Event")
                .description("Event Description")
                .imageUrl("http://example.com/image.jpg")
                .eventDateTime(LocalDateTime.now().plusDays(1))
                .maxParticipants(100)
                .isAvailable(true)
                .isFeatured(true)
                .participants(new ArrayList<>(List.of(participant1, participant2)))
                .build();
    }

    @Test
    void testEventGettersAndSetters() {
        // Test Getters
        assertThat(event.getTitle()).isEqualTo("Sample Event");
        assertThat(event.getDescription()).isEqualTo("Event Description");
        assertThat(event.getImageUrl()).isEqualTo("http://example.com/image.jpg");
        assertThat(event.getEventDateTime()).isAfter(LocalDateTime.now());
        assertThat(event.getMaxParticipants()).isEqualTo(100);
        assertThat(event.getIsAvailable()).isTrue();
        assertThat(event.getIsFeatured()).isTrue();
        
        // Test Participants
        List<Participant> participants = event.getParticipants();
        assertThat(participants).isNotNull();
        assertThat(participants).hasSize(2);
        assertThat(participants).contains(participant1, participant2);
        
        // Test Participants Count
        assertThat(event.getParticipantsCount()).isEqualTo(2);
    }

    @Test
    void testEventBuilder() {
        Event event = Event.builder()
                .title("Another Event")
                .description("Another Description")
                .imageUrl("http://anotherexample.com/image.jpg")
                .eventDateTime(LocalDateTime.now().plusDays(2))
                .maxParticipants(200)
                .isAvailable(false)
                .isFeatured(false)
                .build();

        assertThat(event.getTitle()).isEqualTo("Another Event");
        assertThat(event.getDescription()).isEqualTo("Another Description");
        assertThat(event.getImageUrl()).isEqualTo("http://anotherexample.com/image.jpg");
        assertThat(event.getEventDateTime()).isAfter(LocalDateTime.now());
        assertThat(event.getMaxParticipants()).isEqualTo(200);
        assertThat(event.getIsAvailable()).isFalse();
        assertThat(event.getIsFeatured()).isFalse();
        assertThat(event.getParticipants()).isNull();
        assertThat(event.getParticipantsCount()).isEqualTo(0);
    }

    @Test
    void testEventDefaultConstructor() {
        Event event = new Event();
        assertThat(event.getTitle()).isNull();
        assertThat(event.getDescription()).isNull();
        assertThat(event.getImageUrl()).isNull();
        assertThat(event.getEventDateTime()).isNull();
        assertThat(event.getMaxParticipants()).isEqualTo(0);
        assertThat(event.getIsAvailable()).isNull();
        assertThat(event.getIsFeatured()).isNull();
        assertThat(event.getParticipants()).isNull();
        assertThat(event.getParticipantsCount()).isEqualTo(0);
    }
}
