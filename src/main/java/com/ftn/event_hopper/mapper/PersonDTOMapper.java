package com.ftn.event_hopper.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.event_hopper.models.users.Person;
import com.ftn.event_hopper.models.locations.Location;
import com.ftn.event_hopper.dtos.users.person.SimplePersonDTO;
import com.ftn.event_hopper.dtos.location.SimpleLocationDTO;

@Component
public class PersonDTOMapper {

    private final ModelMapper modelMapper;
    private final LocationDTOMapper locationDTOMapper;

    @Autowired
    public PersonDTOMapper(ModelMapper modelMapper, LocationDTOMapper locationDTOMapper) {
        this.modelMapper = modelMapper;
        this.locationDTOMapper = locationDTOMapper;
        configureMappings();
    }

    private void configureMappings() {
        // Custom converter for nested Location to SimpleLocationDTO
        Converter<Location, SimpleLocationDTO> locationConverter = context ->
                locationDTOMapper.fromLocationToSimpleDTO(context.getSource());

        // Custom mapping for Person -> SimplePersonDTO
        modelMapper.typeMap(Person.class, SimplePersonDTO.class)
                .addMappings(mapper -> mapper.using(locationConverter)
                        .map(Person::getLocation, SimplePersonDTO::setLocation));
    }

    public SimplePersonDTO fromPersonToDTO(Person person) {
        return modelMapper.map(person, SimplePersonDTO.class);
    }
}
