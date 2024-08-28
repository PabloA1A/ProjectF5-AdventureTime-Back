package org.forkingaround.adventuretime.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.forkingaround.adventuretime.dtos.EventDto;
import org.forkingaround.adventuretime.models.Event;
import org.forkingaround.adventuretime.services.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class HomeControllerTest {

    @Mock
    private EventService eventService;

    @InjectMocks
    private HomeController homeController;

    private EventDto eventDto;
    private Event event;

    @BeforeEach
    void setUp() {
        eventDto = new EventDto(
            1L,
            "Sample Event",
            "This is a sample event.",
            "http://example.com/sample.jpg",
            LocalDateTime.now(),
            100,
            true,
            false,
            50
        );

        event = new Event();
        event.setId(1L);
        event.setTitle("Sample Event");
        event.setDescription("This is a sample event.");
        event.setImageUrl("http://example.com/sample.jpg");
        event.setEventDateTime(LocalDateTime.now());
        event.setMaxParticipants(100);
        event.setIsAvailable(true);
        event.setIsFeatured(false);
        event.setParticipants(Arrays.asList());
    }

    @Test
    void testGetAllEvents() {
        List<EventDto> eventList = Arrays.asList(eventDto);
        when(eventService.getAllEvents()).thenReturn(eventList);

        ResponseEntity<List<EventDto>> response = homeController.getAllEvents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(eventList, response.getBody());
        verify(eventService).getAllEvents();
    }

    @Test
    void testGetFeaturedEvents() {
        List<EventDto> featuredEventList = Arrays.asList(eventDto);
        when(eventService.getFeaturedEvents()).thenReturn(featuredEventList);

        ResponseEntity<List<EventDto>> response = homeController.getFeaturedEvents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(featuredEventList, response.getBody());
        verify(eventService).getFeaturedEvents();
    }
}
