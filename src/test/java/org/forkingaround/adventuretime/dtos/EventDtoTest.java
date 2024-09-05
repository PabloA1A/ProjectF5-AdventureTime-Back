// package org.forkingaround.adventuretime.dtos;

// import org.junit.jupiter.api.Test;
// import java.time.LocalDateTime;
// import java.util.Arrays;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// public class EventDtoTest {

//     @Test
//     public void testConstructorAndGetters() {
//         LocalDateTime eventDateTime = LocalDateTime.of(2024, 9, 5, 15, 30);
//         EventDto eventDto = new EventDto(
//             1L,
//             "Test Event",
//             "This is a test event.",
//             "http://example.com/image.jpg",
//             eventDateTime,
//             100,
//             true,
//             false,
//             10,
//             Arrays.asList(1L, 2L, 3L)
//         );

//         assertEquals(1L, eventDto.getId());
//         assertEquals("Test Event", eventDto.getTitle());
//         assertEquals("This is a test event.", eventDto.getDescription());
//         assertEquals("http://example.com/image.jpg", eventDto.getImageUrl());
//         assertEquals(eventDateTime, eventDto.getEventDateTime());
//         assertEquals(100, eventDto.getMaxParticipants());
//         assertTrue(eventDto.getIsAvailable());
//         assertEquals(false, eventDto.getIsFeatured());
//         assertEquals(10, eventDto.getParticipantsCount());
//         assertEquals(Arrays.asList(1L, 2L, 3L), eventDto.getRegistered());
//     }

//     @Test
//     public void testSetters() {
//         EventDto eventDto = new EventDto();

//         eventDto.setId(2L);
//         eventDto.setTitle("New Event");
//         eventDto.setDescription("This is a new event.");
//         eventDto.setImageUrl("http://example.com/newimage.jpg");
//         eventDto.setEventDateTime(LocalDateTime.of(2024, 10, 1, 12, 0));
//         eventDto.setMaxParticipants(200);
//         eventDto.setIsAvailable(false);
//         eventDto.setIsFeatured(true);
//         eventDto.setParticipantsCount(20);
//         eventDto.setRegistered(Arrays.asList(4L, 5L));

//         assertEquals(2L, eventDto.getId());
//         assertEquals("New Event", eventDto.getTitle());
//         assertEquals("This is a new event.", eventDto.getDescription());
//         assertEquals("http://example.com/newimage.jpg", eventDto.getImageUrl());
//         assertEquals(LocalDateTime.of(2024, 10, 1, 12, 0), eventDto.getEventDateTime());
//         assertEquals(200, eventDto.getMaxParticipants());
//         assertEquals(false, eventDto.getIsAvailable());
//         assertEquals(true, eventDto.getIsFeatured());
//         assertEquals(20, eventDto.getParticipantsCount());
//         assertEquals(Arrays.asList(4L, 5L), eventDto.getRegistered());
//     }

//     @Test
//     public void testEqualsAndHashCode() {
//         LocalDateTime eventDateTime = LocalDateTime.of(2024, 9, 5, 15, 30);
//         EventDto eventDto1 = new EventDto(
//             1L,
//             "Test Event",
//             "This is a test event.",
//             "http://example.com/image.jpg",
//             eventDateTime,
//             100,
//             true,
//             false,
//             10,
//             Arrays.asList(1L, 2L, 3L)
//         );

//         EventDto eventDto2 = new EventDto(
//             1L,
//             "Test Event",
//             "This is a test event.",
//             "http://example.com/image.jpg",
//             eventDateTime,
//             100,
//             true,
//             false,
//             10,
//             Arrays.asList(1L, 2L, 3L)
//         );

//         assertEquals(eventDto1, eventDto2);
//         assertEquals(eventDto1.hashCode(), eventDto2.hashCode());
//     }

//     @Test
//     public void testToString() {
//         LocalDateTime eventDateTime = LocalDateTime.of(2024, 9, 5, 15, 30);
//         EventDto eventDto = new EventDto(
//             1L,
//             "Test Event",
//             "This is a test event.",
//             "http://example.com/image.jpg",
//             eventDateTime,
//             100,
//             true,
//             false,
//             10,
//             Arrays.asList(1L, 2L, 3L)
//         );

//         String expectedString = "EventDto(id=1, title=Test Event, description=This is a test event., imageUrl=http://example.com/image.jpg, eventDateTime=2024-09-05T15:30, maxParticipants=100, isAvailable=true, isFeatured=false, participantsCount=10, registered=[1, 2, 3])";
//         assertEquals(expectedString, eventDto.toString());
//     }
// }
