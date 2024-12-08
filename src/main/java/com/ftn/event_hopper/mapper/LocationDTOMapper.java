package com.ftn.event_hopper.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.event_hopper.models.locations.Location;
import com.ftn.event_hopper.dtos.location.SimpleLocationDTO;
import com.ftn.event_hopper.dtos.location.UpdatedLocationDTO;

@Component
public class LocationDTOMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public LocationDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SimpleLocationDTO fromLocationToSimpleDTO(Location location) {
        return modelMapper.map(location, SimpleLocationDTO.class);
    }

    public UpdatedLocationDTO fromLocationToUpdatedDTO(Location location) {
        return modelMapper.map(location, UpdatedLocationDTO.class);
    }

    public Location fromSimpleDTOToLocation(SimpleLocationDTO simpleLocationDTO) {
        return modelMapper.map(simpleLocationDTO, Location.class);
    }

    public Location fromUpdatedDTOToLocation(UpdatedLocationDTO updatedLocationDTO) {
        return modelMapper.map(updatedLocationDTO, Location.class);
    }
    
    public Location fromSimpleLocationDTOToLocation(SimpleLocationDTO locationDTO) {
        return modelMapper.map(locationDTO, Location.class);
    }

    public Location fromLocationDTOToLocation(SimpleLocationDTO locationDTO) {
        return modelMapper.map(locationDTO, Location.class);
    }



}
