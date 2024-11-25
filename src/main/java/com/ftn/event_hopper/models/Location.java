package com.ftn.event_hopper.models;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode

public class Location {
    private UUID id;
    private double latitude;
    private double longitude;
    private String address;
    private String city;
}
