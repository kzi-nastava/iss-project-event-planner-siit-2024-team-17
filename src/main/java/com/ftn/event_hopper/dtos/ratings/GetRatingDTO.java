package com.ftn.event_hopper.dtos.ratings;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class GetRatingDTO {
    private UUID id;
    private int value;
}
