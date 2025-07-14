package com.ftn.event_hopper.models.events;

import com.ftn.event_hopper.models.users.Account;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Table(name = "event_ratings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventRating {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private int value;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

}
