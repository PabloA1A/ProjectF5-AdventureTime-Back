package org.forkingaround.adventuretime.controllers;

import java.util.List;
import java.util.Optional;

import org.forkingaround.adventuretime.dtos.EventDto;
import org.forkingaround.adventuretime.dtos.ParticipantDto;
import org.forkingaround.adventuretime.exceptions.ParticipantNotFoundException;
import org.forkingaround.adventuretime.services.EmailService;
import org.forkingaround.adventuretime.services.EventService;
import org.forkingaround.adventuretime.services.ParticipantService;
import org.forkingaround.adventuretime.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "${api-endpoint}/participant")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private EventService eventService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ProfileService profileService;

    @GetMapping("/all")
    public ResponseEntity<List<ParticipantDto>> getAllParticipants() {
        try {
            List<ParticipantDto> participants = participantService.getAllParticipants();
            return ResponseEntity.ok(participants);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{eventId}/{id}")
    public ResponseEntity<ParticipantDto> getParticipantById(@PathVariable Long id) {
        try {
            ParticipantDto participantDto = participantService.getParticipantById(id);
            return ResponseEntity.ok(participantDto);
        } catch (ParticipantNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/{eventId}/join/{userId}")
    public ResponseEntity<String> joinEvent(@PathVariable Long eventId, @PathVariable Long userId) {
        try {
            boolean isJoined = participantService.joinEvent(eventId, userId);
            if (isJoined) {
                Optional<EventDto> eventOptional = eventService.getEventById(eventId);
                String userEmail = profileService.getEmailByUserId(userId);

                if (eventOptional.isPresent() && userEmail != null) {
                    String evenTitle = eventOptional.get().getTitle();
                    String subject = "Joined to the event: " + evenTitle;
                    String text = "Succesfully joined to the event: " + evenTitle;

                    emailService.sendEmail(userEmail, subject, text);
                }
                return ResponseEntity.ok("Joined successfully");

            } else {
                Optional<EventDto> eventOptional = eventService.getEventById(eventId);
                if (eventOptional.isPresent()
                        && eventOptional.get().getParticipantsCount() >= eventOptional.get().getMaxParticipants()) {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Event is full");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body("User is already registered for the event");
                }
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping("/{eventId}/{participantId}")
    public ResponseEntity<String> deleteParticipant(@PathVariable Long eventId, @PathVariable Long participantId) {
        try {
            participantService.deleteParticipant(eventId, participantId);
            return ResponseEntity.status(HttpStatus.OK).body("Participant deleted successfully");
        } catch (ParticipantNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Participant not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the participant: " + e.getMessage());
        }
    }

    @DeleteMapping("/{eventId}/unregister/{userId}")
    public ResponseEntity<String> unregisterFromEvent(@PathVariable Long eventId, @PathVariable Long userId) {
        try {
            participantService.unregisterFromEvent(eventId, userId);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully unregistered from the event");
        } catch (ParticipantNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Participant not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while unregistering from the event: " + e.getMessage());
        }
    }

}
