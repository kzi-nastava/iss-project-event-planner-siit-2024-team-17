package com.ftn.event_hopper.dtos.reports;

import com.ftn.event_hopper.models.users.Person;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class CreateReportDTO {

    private String reason;
    private UUID reporterId;
    private UUID reportedId;
}
