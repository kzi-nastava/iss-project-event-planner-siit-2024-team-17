package com.ftn.event_hopper.models.messages;

import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.models.users.Person;
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

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String content;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "to_id", nullable = false)
    private Account to;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "from_id", nullable = false)
    private Account from;
}
