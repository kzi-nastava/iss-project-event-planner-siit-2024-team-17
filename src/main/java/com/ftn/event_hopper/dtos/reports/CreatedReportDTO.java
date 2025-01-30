package com.ftn.event_hopper.dtos.reports;

import com.ftn.event_hopper.dtos.users.account.SimpleAccountDTO;
import com.ftn.event_hopper.models.users.Person;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CreatedReportDTO {

    private UUID id;
    private String reason;
    private LocalDateTime timestamp;
    private SimpleAccountDTO reporter;
    private SimpleAccountDTO reported;
}
