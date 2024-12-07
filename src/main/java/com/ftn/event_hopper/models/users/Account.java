package com.ftn.event_hopper.models.users;

import com.ftn.event_hopper.models.registration.RegistrationRequest;
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
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isVerified;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = true)
    private LocalDateTime suspensionTimestamp;

    @Column(nullable = true)
    private PersonType type;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = true)
    private Person person;

    @OneToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "registration_request_id")
    private RegistrationRequest registrationRequest;
}
