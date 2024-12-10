package com.ftn.event_hopper.mapper.eventTypes;

import com.ftn.event_hopper.dtos.eventTypes.SimpleEventTypeDTO;
import com.ftn.event_hopper.models.eventTypes.EventType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

}
