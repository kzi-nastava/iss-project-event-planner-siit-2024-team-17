package com.ftn.event_hopper.mapper;

import com.ftn.event_hopper.models.eventTypes.EventType;
import org.modelmapper.ModelMapper;
import org.modelmapper.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.event_hopper.dtos.events.SimpleEventDTO;
import com.ftn.event_hopper.dtos.eventTypes.SimpleEventTypeDTO;
import com.ftn.event_hopper.dtos.location.SimpleLocationDTO;
import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.locations.Location;

@Component
public class EventDTOMapper {
    private final ModelMapper modelMapper;
    private final LocationDTOMapper locationDTOMapper;

    @Autowired
    public EventDTOMapper(ModelMapper modelMapper, LocationDTOMapper locationDTOMapper) {
        this.modelMapper = modelMapper;
        this.locationDTOMapper = locationDTOMapper;
        configureMappings();
    }

    private void configureMappings() {
        // Custom converter for Location -> SimpleLocationDTO
        Converter<Location, SimpleLocationDTO> locationConverter = context ->
                locationDTOMapper.fromLocationToSimpleDTO(context.getSource());

        // Custom mapping for Event -> SimpleEventDTO
        modelMapper.typeMap(Event.class, SimpleEventDTO.class)
                .addMappings(mapper -> mapper.using(locationConverter)
                        .map(Event::getLocation, SimpleEventDTO::setLocation));

        // Custom mapping for EventType -> SimpleEventTypeDTO (if needed)
        modelMapper.typeMap(EventType.class, SimpleEventTypeDTO.class);
    }

    public SimpleEventDTO fromEventToSimpleDTO(Event event) {
        return modelMapper.map(event, SimpleEventDTO.class);
    }
}
