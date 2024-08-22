package org.forkingaround.adventuretime.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "participants")
@Data
@AllArgsConstructor
@Builder
public class Participants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    private Long event_id;
    @Column(nullable = false)
    private Long user_id;
    @Column(nullable = false)
    private LocalDateTime joined_at;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Events event;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    
}
