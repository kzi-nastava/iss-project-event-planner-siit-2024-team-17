package com.ftn.event_hopper.dtos.users.account;

import com.ftn.event_hopper.dtos.registration.CreateRegistrationRequestDTO;
import com.ftn.event_hopper.dtos.users.serviceProvider.CreateServiceProviderDTO;
import com.ftn.event_hopper.models.users.PersonType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CreateServiceProviderAccountDTO {
    private String email;
    private String password;
    private boolean isVerified;
    private LocalDateTime suspensionTimeStamp;
    private PersonType type;
    private CreateServiceProviderDTO person;
    private CreateRegistrationRequestDTO registrationRequest;
}
