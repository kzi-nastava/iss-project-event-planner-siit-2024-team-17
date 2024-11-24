package com.ftn.event_hopper.model;


import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Reservation {
    private UUID id;
    private LocalDateTime timestamp;
    private LocalDateTime from;
    private LocalDateTime to;
    private Product product;
    private Event event;
}
