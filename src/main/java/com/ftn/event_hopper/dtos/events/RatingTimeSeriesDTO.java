package com.ftn.event_hopper.dtos.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingTimeSeriesDTO {
    private LocalDateTime timestamp;
    private double averageRating;
}
