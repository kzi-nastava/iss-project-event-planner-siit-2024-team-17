package com.ftn.event_hopper.models.ratings;

import com.ftn.event_hopper.models.users.EventOrganizer;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Rating {
    private UUID id;
    private int value;
    private EventOrganizer eventOrganizer;
}
