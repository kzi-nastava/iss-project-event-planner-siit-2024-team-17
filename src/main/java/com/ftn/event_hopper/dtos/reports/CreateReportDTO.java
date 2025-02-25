package com.ftn.event_hopper.dtos.reports;

import com.ftn.event_hopper.dtos.users.account.SimpleAccountDTO;
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
    private UUID reported;
}
