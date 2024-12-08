package com.ftn.event_hopper.services;

import com.ftn.event_hopper.dtos.location.*;
import com.ftn.event_hopper.models.locations.Location;
import com.ftn.event_hopper.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ftn.event_hopper.mapper.LocationDTOMapper;

import java.util.List;
import java.util.UUID;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private LocationDTOMapper locationDTOMapper;

    public SimpleLocationDTO findOneSimple(UUID id) {
        Location location = locationRepository.findById(id).orElseGet(null);
        return locationDTOMapper.fromLocationToSimpleDTO(location);
    }

    public LocationDTO findOneLocation(UUID id) {
        Location location = locationRepository.findById(id).orElseGet(null);
        return locationDTOMapper.fromLocationToLocationDTO(location);
    }

    public List<SimpleLocationDTO> findAllSimpleLocations() {
        List<Location> locations = locationRepository.findAll();
        return locationDTOMapper.fromLocationListToSimpleDTOList(locations);
    }

    public List<LocationDTO> findAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return locationDTOMapper.fromLocationListToDTOList(locations);
    }

    public CreatedLocationDTO create(CreateLocationDTO locationDTO){
        Location location = locationDTOMapper.fromCreateDTOToLocation(locationDTO);
        this.save(location);
        return locationDTOMapper.fromLocationToCreatedDTO(location);
    }

    public UpdatedLocationDTO update(UUID id, UpdateLocationDTO locationDTO) {
        Location location = locationRepository.findById(id).orElseGet(null);
        if(location!= null) {
            location.setCity(locationDTO.getCity());
            location.setAddress(locationDTO.getAddress());
            location.setLatitude(locationDTO.getLatitude());
            location.setLongitude(locationDTO.getLongitude());
            this.save(location);
        }
        return locationDTOMapper.fromLocationToUpdatedDTO(location);
    }

    public Location save(Location location) {
        return locationRepository.save(location);
    }



}
