package org.forkingaround.adventuretime.dtos;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class EventDtoTest {

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Long id = 1L;
        String title = "Sample Event";
        String description = "Event Description";
        String imageUrl = "http://example.com/image.jpg";
        LocalDateTime eventDateTime = LocalDateTime.now().plusDays(1);
        int maxParticipants = 100;
        Boolean isAvailable = true;
        Boolean isFeatured = true;
        int participantsCount = 10;

        // Act
        EventDto eventDto = new EventDto(id, title, description, imageUrl, eventDateTime, maxParticipants, isAvailable, isFeatured, participantsCount);

        // Assert
        assertThat(eventDto.getId()).isEqualTo(id);
        assertThat(eventDto.getTitle()).isEqualTo(title);
        assertThat(eventDto.getDescription()).isEqualTo(description);
        assertThat(eventDto.getImageUrl()).isEqualTo(imageUrl);
        assertThat(eventDto.getEventDateTime()).isEqualTo(eventDateTime);
        assertThat(eventDto.getMaxParticipants()).isEqualTo(maxParticipants);
        assertThat(eventDto.getIsAvailable()).isEqualTo(isAvailable);
        assertThat(eventDto.getIsFeatured()).isEqualTo(isFeatured);
        assertThat(eventDto.getParticipantsCount()).isEqualTo(participantsCount);
    }

    @Test
    void testGettersAndSetters() {
        // Arrange
        Long id = 1L;
        String title = "Sample Event";
        String description = "Event Description";
        String imageUrl = "http://example.com/image.jpg";
        LocalDateTime eventDateTime = LocalDateTime.now().plusDays(1);
        int maxParticipants = 100;
        Boolean isAvailable = true;
        Boolean isFeatured = true;
        int participantsCount = 10;

        EventDto eventDto = new EventDto(id, title, description, imageUrl, eventDateTime, maxParticipants, isAvailable, isFeatured, participantsCount);

        // Act & Assert
        assertThat(eventDto.getId()).isEqualTo(id);
        assertThat(eventDto.getTitle()).isEqualTo(title);
        assertThat(eventDto.getDescription()).isEqualTo(description);
        assertThat(eventDto.getImageUrl()).isEqualTo(imageUrl);
        assertThat(eventDto.getEventDateTime()).isEqualTo(eventDateTime);
        assertThat(eventDto.getMaxParticipants()).isEqualTo(maxParticipants);
        assertThat(eventDto.getIsAvailable()).isEqualTo(isAvailable);
        assertThat(eventDto.getIsFeatured()).isEqualTo(isFeatured);
        assertThat(eventDto.getParticipantsCount()).isEqualTo(participantsCount);
    }

    @Test
    void testToString() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        EventDto eventDto = new EventDto(1L, "Sample Event", "Event Description", "http://example.com/image.jpg", now, 100, true, true, 10);

        // Act
        String toStringResult = eventDto.toString();

        // Assert
        assertThat(toStringResult).contains("EventDto(id=1, title=Sample Event, description=Event Description, imageUrl=http://example.com/image.jpg, eventDateTime=");
        assertThat(toStringResult).contains(", maxParticipants=100, isAvailable=true, isFeatured=true, participantsCount=10)");
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        EventDto eventDto1 = new EventDto(1L, "Sample Event", "Event Description", "http://example.com/image.jpg", now, 100, true, true, 10);
        EventDto eventDto2 = new EventDto(1L, "Sample Event", "Event Description", "http://example.com/image.jpg", now, 100, true, true, 10);

        // Act & Assert
        assertThat(eventDto1).isEqualTo(eventDto2);
        assertThat(eventDto1.hashCode()).isEqualTo(eventDto2.hashCode());
    }
}
