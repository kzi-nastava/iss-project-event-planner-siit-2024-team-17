package com.ftn.event_hopper.dtos.reservations;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CreateReservationServiceDTO {
    private UUID eventId;
    private UUID productId;
    private LocalDateTime from;
    private LocalDateTime to;
}