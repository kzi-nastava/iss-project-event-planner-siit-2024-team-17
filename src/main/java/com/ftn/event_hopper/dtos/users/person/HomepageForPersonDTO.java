package com.ftn.event_hopper.dtos.users.person;

import com.ftn.event_hopper.dtos.events.SimpleEventDTO;
import com.ftn.event_hopper.dtos.location.SimpleLocationDTO;
import com.ftn.event_hopper.dtos.solutions.SimpleProductDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HomepageForPersonDTO {
    private String name;
    private String surname;
    private SimpleLocationDTO location;
    private List<SimpleEventDTO> favoriteEvents;
    private List<SimpleProductDTO> favoriteProducts;
}
