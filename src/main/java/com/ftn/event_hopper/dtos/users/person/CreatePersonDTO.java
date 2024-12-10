package com.ftn.event_hopper.dtos.users.person;

import com.ftn.event_hopper.dtos.location.LocationDTO;
import com.ftn.event_hopper.dtos.location.SimpleLocationDTO;
import com.ftn.event_hopper.models.users.PersonType;
import lombok.*;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreatePersonDTO {
    private String name;
    private String surname;
    private String profilePicture;
    private String phoneNumber;
    private PersonType type;
    private SimpleLocationDTO location;
}
