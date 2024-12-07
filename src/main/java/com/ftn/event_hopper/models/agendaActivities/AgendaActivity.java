package com.ftn.event_hopper.models.agendaActivities;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AgendaActivity {
    private UUID id;
    private String name;
    private String description;
    private String locationName;
    private LocalDateTime start;
    private LocalDateTime end;
}
