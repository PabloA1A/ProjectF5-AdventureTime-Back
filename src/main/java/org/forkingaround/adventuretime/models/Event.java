package org.forkingaround.adventuretime.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
@Data
@AllArgsConstructor
@Builder

public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "event_datetime", nullable = false)
    private LocalDateTime eventDateTime;

    @Column(name = "max_participants", nullable = false)
    private int maxParticipants;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;

    @Column(name = "is_featured", nullable = false)
    private Boolean isFeatured;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participant> participants;
    
}
