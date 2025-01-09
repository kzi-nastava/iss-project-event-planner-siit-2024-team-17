package com.ftn.event_hopper.dtos.events;

import com.ftn.event_hopper.dtos.location.CreateLocationDTO;
import com.ftn.event_hopper.models.shared.EventPrivacyType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CreateEventDTO {
    private String name;
    private int maxAttendance;
    private String description;
    private EventPrivacyType eventPrivacyType;
    private LocalDateTime time;
    private String picture;
    private UUID eventTypeId;
    private List<CreateAgendaActivityDTO> agendaActivities;
    private CreateLocationDTO location;
}
