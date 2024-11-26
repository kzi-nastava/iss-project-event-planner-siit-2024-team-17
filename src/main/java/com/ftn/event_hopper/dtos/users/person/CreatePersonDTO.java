package com.ftn.event_hopper.dtos.users.person;

import com.ftn.event_hopper.models.shared.Location;
import lombok.*;

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
    private String type;
    private Location location;
}
