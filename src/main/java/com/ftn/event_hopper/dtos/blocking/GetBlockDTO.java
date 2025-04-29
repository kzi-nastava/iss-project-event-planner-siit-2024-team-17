package com.ftn.event_hopper.dtos.blocking;

import com.ftn.event_hopper.dtos.users.account.SimpleAccountDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetBlockDTO {
    private UUID id;
    private LocalDateTime timestamp;
    private SimpleAccountDTO who;
    private SimpleAccountDTO blocked;
}
