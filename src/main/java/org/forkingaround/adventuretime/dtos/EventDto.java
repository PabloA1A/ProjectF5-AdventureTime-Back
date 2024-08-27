package org.forkingaround.adventuretime.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventDto {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private LocalDateTime eventDateTime;
    private int maxParticipants;
    private Boolean isAvailable;
    private Boolean isFeatured;
    private int participantsCount;
}
