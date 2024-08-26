package org.forkingaround.adventuretime.dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EventRequest {
    private String title;
    private String description;
    private String imageUrl;
    private LocalDateTime eventDateTime;
    private int maxParticipants;
    private Boolean isAvailable;
    private Boolean isFeatured;
}
