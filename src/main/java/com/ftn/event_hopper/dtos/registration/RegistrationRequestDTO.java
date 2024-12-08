package com.ftn.event_hopper.dtos.registration;

import com.ftn.event_hopper.models.registration.RegistrationRequestStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationRequestDTO {
    private UUID id;
    private LocalDateTime timestamp;
    private RegistrationRequestStatus status;
}

