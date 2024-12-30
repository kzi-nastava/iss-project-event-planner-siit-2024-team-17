package com.ftn.event_hopper.mapper.eventTypes;

import com.ftn.event_hopper.dtos.eventTypes.SimpleEventTypeDTO;
import com.ftn.event_hopper.dtos.events.SimpleEventDTO;
import com.ftn.event_hopper.models.eventTypes.EventType;
import com.ftn.event_hopper.models.events.Event;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventTypeDTOMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public EventTypeDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SimpleEventTypeDTO fromEventTypeToSimpleDTO(EventType eventType) {
        return modelMapper.map(eventType, SimpleEventTypeDTO.class);
    }

    public List<SimpleEventTypeDTO> fromEventTypeListToSimpleDTOList(List<EventType> eventTypes) {
        return eventTypes.stream()
                .map(this::fromEventTypeToSimpleDTO)
                .collect(Collectors.toList());
    }

}
