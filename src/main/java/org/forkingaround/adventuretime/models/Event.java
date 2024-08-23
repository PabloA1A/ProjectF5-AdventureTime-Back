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
    private String tittle;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String image_url;

    @Column(nullable = false)
    private LocalDateTime event_datetime;

    @Column(nullable = false)
    private Integer max_participants;

    @Column(nullable = false)
    private Boolean is_available;

    @Column(nullable = false)
    private Boolean is_featured;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participant> participants;
    
}
