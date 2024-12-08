package com.ftn.event_hopper.dtos.users.person;

import com.ftn.event_hopper.dtos.events.SimpleEventDTO;
import com.ftn.event_hopper.dtos.location.SimpleLocationDTO;
import com.ftn.event_hopper.dtos.solutions.SimpleProductDTO;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfileInfoForPersonDTO {
    private UUID id;
    private String name;
    private String surname;
    private String profilePicture;
    private String phoneNumber;
    private SimpleLocationDTO location;
    private List<SimpleEventDTO> attendingEvents;
    private List<SimpleEventDTO> favoriteEvents;
    private List<SimpleProductDTO> favoriteProducts;
}
