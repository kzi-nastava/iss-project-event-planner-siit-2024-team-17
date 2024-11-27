package com.ftn.event_hopper.dtos.reservations;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CreatedReservationProductDTO {
    private UUID id;
    private UUID eventId;
    private UUID productId;
    private LocalDateTime timestamp;
}
