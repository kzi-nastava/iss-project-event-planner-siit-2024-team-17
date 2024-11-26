package com.ftn.event_hopper.models.shared;

import lombok.*;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Location {
    private UUID id;
    private String city;
    private String address;
    private double latitude;
    private double longitude;
}
