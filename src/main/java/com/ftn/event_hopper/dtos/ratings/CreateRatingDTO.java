package com.ftn.event_hopper.dtos.ratings;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CreateRatingDTO {
    int value;
    UUID eventId;
}
