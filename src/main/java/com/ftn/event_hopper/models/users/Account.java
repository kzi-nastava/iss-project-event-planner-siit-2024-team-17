package com.ftn.event_hopper.models.users;

import com.ftn.event_hopper.models.registration.RegistrationRequest;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Account {
    private UUID id;
    private String email;
    private String password;
    private boolean isVerified;
    private boolean isActive;
    private LocalDateTime suspensionTimestamp;
    private PersonType type;
    private Person person;
    private RegistrationRequest registrationRequest;
}
