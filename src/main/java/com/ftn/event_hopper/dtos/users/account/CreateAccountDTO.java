package com.ftn.event_hopper.dtos.users.account;

import com.ftn.event_hopper.dtos.registration.CreateRegistrationRequestDTO;
import com.ftn.event_hopper.dtos.registration.RegistrationRequestDTO;
import com.ftn.event_hopper.dtos.users.person.CreatePersonDTO;
import com.ftn.event_hopper.models.users.PersonType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CreateAccountDTO {
    private String email;
    private String password;
    private boolean isVerified;
    private boolean isActive;
    private LocalDateTime suspensionTimeStamp;
    private PersonType type;
    private CreatePersonDTO person;
    private CreateRegistrationRequestDTO registrationRequest;
}
