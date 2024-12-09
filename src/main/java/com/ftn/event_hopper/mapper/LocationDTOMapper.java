package com.ftn.event_hopper.mapper;

import com.ftn.event_hopper.dtos.location.*;
import com.ftn.event_hopper.dtos.users.person.SimplePersonDTO;
import com.ftn.event_hopper.models.users.Person;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.event_hopper.models.locations.Location;

import java.util.List;
import java.util.stream.Collectors;

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

    public LocationDTO fromLocationToLocationDTO(Location location) {
        return modelMapper.map(location, LocationDTO.class);
    }

    public CreatedLocationDTO fromLocationToCreatedDTO(Location location) {
        return modelMapper.map(location, CreatedLocationDTO.class);
    }

    public UpdatedLocationDTO fromLocationToUpdatedDTO(Location location) {
        return modelMapper.map(location, UpdatedLocationDTO.class);
    }

    public Location fromSimpleDTOToLocation(SimpleLocationDTO simpleLocationDTO) {
        return modelMapper.map(simpleLocationDTO, Location.class);
    }

    public Location fromCreateDTOToLocation(CreateLocationDTO createLocationDTO) {
        return modelMapper.map(createLocationDTO, Location.class);
    }

    public Location fromUpdatedDTOToLocation(UpdatedLocationDTO updatedLocationDTO) {
        return modelMapper.map(updatedLocationDTO, Location.class);
    }

    public Location fromSimpleLocationDTOToLocation(SimpleLocationDTO locationDTO) {
        return modelMapper.map(locationDTO, Location.class);
    }

    public Location fromLocationDTOToLocation(LocationDTO locationDTO) {
        return modelMapper.map(locationDTO, Location.class);
    }

    public List<SimpleLocationDTO> fromLocationListToSimpleDTOList(List<Location> persons) {
        return persons.stream()
                .map(this::fromLocationToSimpleDTO)
                .collect(Collectors.toList());
    }

    public List<LocationDTO> fromLocationListToDTOList(List<Location> persons) {
        return persons.stream()
                .map(this::fromLocationToLocationDTO)
                .collect(Collectors.toList());
    }

}
