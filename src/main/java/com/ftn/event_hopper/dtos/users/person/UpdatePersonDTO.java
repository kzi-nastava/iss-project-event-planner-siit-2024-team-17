package com.ftn.event_hopper.dtos.users.person;

import com.ftn.event_hopper.dtos.location.SimpleLocationDTO;
import com.ftn.event_hopper.models.users.PersonType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePersonDTO {
    private String name;
    private String surname;
    private String phoneNumber;
    private PersonType type;
    private SimpleLocationDTO location;
}
