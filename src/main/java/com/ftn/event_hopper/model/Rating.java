package com.ftn.event_hopper.model;

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
