package com.ftn.event_hopper.dtos.users.account;

import com.ftn.event_hopper.dtos.registration.RegistrationRequestDTO;
import com.ftn.event_hopper.dtos.users.person.SimplePersonDTO;
import com.ftn.event_hopper.models.registration.RegistrationRequestStatus;
import com.ftn.event_hopper.models.users.PersonType;
import com.ftn.event_hopper.models.registration.RegistrationRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CreatedAccountDTO {
    private UUID id;
    private String email;
    private String password;
    private boolean isVerified;
    private boolean isActive;
    private LocalDateTime suspensionTimeStamp;
    private PersonType type;
    private SimplePersonDTO person;
    private RegistrationRequestDTO registrationRequest;
}
