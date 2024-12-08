package com.ftn.event_hopper.dtos.users.person;

import com.ftn.event_hopper.dtos.location.SimpleLocationDTO;
import com.ftn.event_hopper.models.users.PersonType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class SimplePersonDTO {
    private UUID id;
    private String name;
    private String surname;
    private String profilePicture;
    private String phoneNumber;
    private PersonType personType;
    private SimpleLocationDTO location;
}
