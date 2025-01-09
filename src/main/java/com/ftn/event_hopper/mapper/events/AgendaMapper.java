package com.ftn.event_hopper.mapper.events;

import com.ftn.event_hopper.dtos.events.CreateAgendaActivityDTO;
import com.ftn.event_hopper.dtos.events.GetEventAgendasDTO;
import com.ftn.event_hopper.models.events.AgendaActivity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AgendaMapper {
    public GetEventAgendasDTO mapToGetEventAgendasDTO(Set<AgendaActivity> activities) {
        // Map each AgendaActivity to CreateAgendaActivityDTO
        List<CreateAgendaActivityDTO> agendas = activities.stream()
                .map(activity -> new CreateAgendaActivityDTO(
                        activity.getName(),
                        activity.getDescription(),
                        activity.getLocationName(),
                        activity.getStartTime(),
                        activity.getEndTime()
                ))
                .collect(Collectors.toList());

        // Create and return the DTO
        return new GetEventAgendasDTO(agendas);
    }
}
