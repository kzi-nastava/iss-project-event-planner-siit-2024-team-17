package com.ftn.event_hopper.dtos.users.person;

import com.ftn.event_hopper.dtos.location.CreateLocationDTO;

import com.ftn.event_hopper.models.users.PersonType;
import lombok.*;


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
    private CreateLocationDTO location;
}
