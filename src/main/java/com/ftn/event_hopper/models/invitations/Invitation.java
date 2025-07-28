package com.ftn.event_hopper.models.invitations;

import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.shared.InvitationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode

@Table(name = "invitations")
@Entity
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String targetEmail;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private InvitationStatus status;

    @Column
    private String picture;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
}
