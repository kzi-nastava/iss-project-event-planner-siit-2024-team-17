package com.ftn.event_hopper.dtos.events;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CreateAgendaActivityDTO {
    private String name;
    private String description;
    private String locationName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
