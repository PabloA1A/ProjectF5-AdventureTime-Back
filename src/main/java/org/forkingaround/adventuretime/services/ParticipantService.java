package org.forkingaround.adventuretime.services;

import org.forkingaround.adventuretime.dtos.ParticipantDto;
import org.forkingaround.adventuretime.exceptions.ParticipantNotFoundException;
import org.forkingaround.adventuretime.models.Event;
import org.forkingaround.adventuretime.models.Participant;
import org.forkingaround.adventuretime.models.User;
import org.forkingaround.adventuretime.repositories.EventRepository;
import org.forkingaround.adventuretime.repositories.ParticipantRepository;
import org.forkingaround.adventuretime.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    public ParticipantDto addParticipant(ParticipantDto participantDto) {
        Participant participant = new Participant();
        participant.setJoinedAt(LocalDateTime.now());

        Optional<Event> event = eventRepository.findById(participantDto.getEventId());
        event.ifPresent(participant::setEvent);

        Optional<User> user = userRepository.findById(participantDto.getUserId());
        user.ifPresent(participant::setUser);

        Participant savedParticipant = participantRepository.save(participant);

        return convertToDto(savedParticipant);
    }

    public List<ParticipantDto> getAllParticipants() {
        List<Participant> participants = participantRepository.findAll();
        return participants.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ParticipantDto getParticipantById(Long id) {
        Participant participant = participantRepository.findById(id)
                .orElseThrow(() -> new ParticipantNotFoundException("Participant not found with ID: " + id));
        return convertToDto(participant);
    }

    public boolean joinEvent(Long eventId, Long userId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (eventOptional.isPresent()) {
            Event event = eventOptional.get()

            if (event.getParticipantsCount() >= event.getMaxParticipants()) {
                return false;
            }

            boolean isAlreadyRegistered = participantRepository.findByEventIdAndUserId(eventId, userId).isPresent();
            if (isAlreadyRegistered) {
                return false;
            }

            Participant participant = new Participant();
            participant.setEvent(eventOptional.get());
            participant.setUser(userOptional.get());
            participant.setJoinedAt(LocalDateTime.now());

            participantRepository.save(participant);
            return true;
        }

        return false;
    }

    public void deleteParticipant(Long eventId, Long participantId) {

        if (!eventRepository.existsById(eventId)) {
            throw new ParticipantNotFoundException("Event not found with ID: " + eventId);
        }

        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new ParticipantNotFoundException("Participant not found with ID: " + participantId));

        if (!participant.getEvent().getId().equals(eventId)) {
            throw new ParticipantNotFoundException(
                    "Participant with ID: " + participantId + " is not associated with event ID: " + eventId);
        }
        participantRepository.delete(participant);
    }

    public void unregisterFromEvent(Long eventId, Long userId) {  
        
        Long authenticatedUserId = getAuthenticatedUserId();

        if (!authenticatedUserId.equals(userId)) {
            throw new SecurityException("You can only unregister yourself from the event.");
        }
            
        Participant participant = participantRepository.findByEventIdAndUserId(eventId, userId)
                .orElseThrow(() -> new ParticipantNotFoundException("Participant not found for the event with ID: " + eventId + " and user ID: " + userId));
    
        
        participantRepository.delete(participant);
    }

    private Long getAuthenticatedUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && authentication.isAuthenticated()) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            // Asumiendo que tu UserDetails tiene un método getId()
            return ((MyUserDetails) userDetails).getId();  // Cambia MyUserDetails por tu implementación
        } else if (principal instanceof String) {
            // En caso de que el principal sea solo un nombre de usuario
            String username = (String) principal;
            // Busca el usuario en la base de datos usando el nombre de usuario y devuelve su ID
            User user = userRepository.findByUsername(username);
            return user.getId();
        }
         //esto es para poder registrarse y borrarse solo a uno mismo
    }
    
    throw new SecurityException("User is not authenticated");
}

    private ParticipantDto convertToDto(Participant participant) {
        return new ParticipantDto(
                participant.getId(),
                participant.getJoinedAt(),
                participant.getEvent().getId(),
                participant.getUser() != null ? participant.getUser().getId() : null);
    }
}
