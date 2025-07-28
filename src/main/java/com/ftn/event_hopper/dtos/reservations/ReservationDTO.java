package com.ftn.event_hopper.dtos.reservations;

import com.ftn.event_hopper.dtos.events.SimpleEventDTO;
import com.ftn.event_hopper.dtos.solutions.SimpleProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ReservationDTO {
    private UUID id;
    private SimpleEventDTO event;
    private SimpleProductDTO product;
    private LocalDateTime timestamp;
    private LocalDateTime from;
    private LocalDateTime to;
}
