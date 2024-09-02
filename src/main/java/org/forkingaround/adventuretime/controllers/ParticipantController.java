package org.forkingaround.adventuretime.controllers;

import java.util.List;

import org.forkingaround.adventuretime.dtos.ParticipantDto;
import org.forkingaround.adventuretime.exceptions.ParticipantNotFoundException;
import org.forkingaround.adventuretime.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "${api-endpoint}/event")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    
    @GetMapping("/{eventId}/participant/all")
    public ResponseEntity<List<ParticipantDto>> getAllParticipants() {
        try {
            List<ParticipantDto> participants = participantService.getAllParticipants();
            return ResponseEntity.ok(participants);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping("/{eventId}/participant/{id}")
    public ResponseEntity<ParticipantDto> getParticipantById(@PathVariable Long id) {
        try {
            ParticipantDto participantDto = participantService.getParticipantById(id);
            return ResponseEntity.ok(participantDto);
        } catch (ParticipantNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
    @PostMapping("/{eventId}/participant/add")
    public ResponseEntity<ParticipantDto> addParticipant(@RequestBody ParticipantDto participantDto) {
        try {
            ParticipantDto savedParticipant = participantService.addParticipant(participantDto);
            return ResponseEntity.ok(savedParticipant);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/{eventId}/participant/join/{userId}")
    public ResponseEntity<String> joinEvent(@PathVariable Long eventId, @PathVariable Long userId) {
        try {
            boolean isJoined = participantService.joinEvent(eventId, userId);
            if (isJoined) {
                return ResponseEntity.ok("Joined successfully");
            } else {
                return ResponseEntity.status(406).body("Joining not possible");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping("/{eventId}/participants/{participantId}")
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
}
