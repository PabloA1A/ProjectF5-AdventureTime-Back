package org.forkingaround.adventuretime.controllers;

import java.util.List;

import org.forkingaround.adventuretime.dtos.EventDto;
import org.forkingaround.adventuretime.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${api-endpoint}/home")
public class HomeController {

    @Autowired
    private EventService eventService;

    @GetMapping("/allevent")
    public ResponseEntity<List<EventDto>> getAllEvents() {
        List<EventDto> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/eventfeatured")
    public ResponseEntity<List<EventDto>> getFeaturedEvents() {
        List<EventDto> featuredEvents = eventService.getFeaturedEvents();
        return ResponseEntity.ok(featuredEvents);
    }

}
