package org.forkingaround.adventuretime.controllers;

import java.util.List;
import java.util.Optional;

import org.forkingaround.adventuretime.dtos.EventRequest;
import org.forkingaround.adventuretime.exceptions.EventException;
import org.forkingaround.adventuretime.exceptions.EventNotFoundException;
import org.forkingaround.adventuretime.models.Event;
import org.forkingaround.adventuretime.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${api-endpoint}/event")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/all")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/featured")
    public ResponseEntity<List<Event>> getFeaturedEvents() {
        List<Event> featuredEvents = eventService.getFeaturedEvents();
        return ResponseEntity.ok(featuredEvents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventService.getEventById(id);
        return event.map(ResponseEntity::ok)
                .orElseThrow(() -> new EventNotFoundException("Event with id " + id + " not found"));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addEvent(@RequestBody EventRequest eventRequest) {
        try {
            Event addEvent = eventService.addEvent(eventRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Event added successfully with id: " + addEvent.getId());
        } catch (EventException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid event data: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateEvent(@PathVariable Long id, @RequestBody EventRequest eventRequest) {
        try {
            eventService.updateEvent(id, eventRequest);
            return ResponseEntity.ok("Event updated successfully");
        } catch (EventNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found: " + e.getMessage());
        } catch (EventException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid event data: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.noContent().build();
        } catch (EventNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found: " + e.getMessage());
        } catch (EventException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the event: " + e.getMessage());
        }
    }

}
