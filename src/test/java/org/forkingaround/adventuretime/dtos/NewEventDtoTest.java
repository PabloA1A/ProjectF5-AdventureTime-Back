package org.forkingaround.adventuretime.dtos;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class NewEventDtoTest {

    @Test
    void testNewEventDtoCreationAndGetters() {
        Long id = 1L;
        String title = "Test Event";
        String description = "This is a test event";
        Optional<String> imageHash = Optional.of("abc123");
        LocalDateTime eventDateTime = LocalDateTime.of(2024, 9, 15, 10, 0);
        int maxParticipants = 50;
        Boolean isAvailable = true;
        Boolean isFeatured = false;

        NewEventDto dto = new NewEventDto(id, title, description, imageHash, eventDateTime, maxParticipants,
                isAvailable, isFeatured);

        assertEquals(id, dto.getId());
        assertEquals(title, dto.getTitle());
        assertEquals(description, dto.getDescription());
        assertEquals(imageHash, dto.getImageHash());
        assertEquals(eventDateTime, dto.getEventDateTime());
        assertEquals(maxParticipants, dto.getMaxParticipants());
        assertEquals(isAvailable, dto.getIsAvailable());
        assertEquals(isFeatured, dto.getIsFeatured());
    }

    @Test
    void testNewEventDtoSetters() {
        
        NewEventDto dto = new NewEventDto();

        dto.setId(2L);
        dto.setTitle("Updated Event");
        dto.setDescription("This is an updated event");
        dto.setImageHash(Optional.empty());
        LocalDateTime newDateTime = LocalDateTime.of(2024, 10, 1, 14, 30);
        dto.setEventDateTime(newDateTime);
        dto.setMaxParticipants(100);
        dto.setIsAvailable(false);
        dto.setIsFeatured(true);

        assertEquals(2L, dto.getId());
        assertEquals("Updated Event", dto.getTitle());
        assertEquals("This is an updated event", dto.getDescription());
        assertTrue(dto.getImageHash().isEmpty());
        assertEquals(newDateTime, dto.getEventDateTime());
        assertEquals(100, dto.getMaxParticipants());
        assertFalse(dto.getIsAvailable());
        assertTrue(dto.getIsFeatured());
    }

}