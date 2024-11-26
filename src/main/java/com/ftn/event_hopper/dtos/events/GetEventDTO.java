package com.ftn.event_hopper.dtos.events;

import com.ftn.event_hopper.models.locations.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class GetEventDTO {

    private String name;
    private String description;
    private String picture;
    private Location location;

}
