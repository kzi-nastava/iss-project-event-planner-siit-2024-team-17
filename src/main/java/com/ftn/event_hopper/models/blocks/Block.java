package com.ftn.event_hopper.models.blocks;

import com.ftn.event_hopper.models.users.Person;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "blocks")
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "who_id", nullable = false)
    private Person who;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "blocked_id", nullable = false)
    private Person blocked;
}
