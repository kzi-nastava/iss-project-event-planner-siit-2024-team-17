package com.ftn.event_hopper.models.registration;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class RegistrationRequest {
    private UUID id;
    private LocalDateTime timestamp;
    private RegistrationRequestStatus status;
}
