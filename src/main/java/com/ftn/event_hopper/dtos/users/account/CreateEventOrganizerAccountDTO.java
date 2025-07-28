package com.ftn.event_hopper.dtos.users.account;

import com.ftn.event_hopper.dtos.registration.CreateRegistrationRequestDTO;
import com.ftn.event_hopper.dtos.users.eventOrganizer.CreateEventOrganizerDTO;
import com.ftn.event_hopper.dtos.users.serviceProvider.CreateServiceProviderDTO;
import com.ftn.event_hopper.models.users.PersonType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CreateEventOrganizerAccountDTO {
    private String email;
    private String profilePicture;
    private String password;
    private boolean isVerified;
    private LocalDateTime suspensionTimeStamp;
    private PersonType type;
    private CreateEventOrganizerDTO person;
    private CreateRegistrationRequestDTO registrationRequest;
}
