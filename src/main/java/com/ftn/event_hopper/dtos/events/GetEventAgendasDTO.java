package com.ftn.event_hopper.dtos.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GetEventAgendasDTO {
    List<CreateAgendaActivityDTO> agendas;
}
