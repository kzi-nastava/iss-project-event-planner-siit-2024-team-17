package com.ftn.event_hopper.models;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode

public class Report {
    private UUID id;
    private String reason;
    private LocalDateTime timestamp;
    private Person reporter;
    private Person reported;
}
