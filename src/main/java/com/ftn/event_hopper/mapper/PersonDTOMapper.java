package com.ftn.event_hopper.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.event_hopper.models.users.Person;
import com.ftn.event_hopper.models.locations.Location;
import com.ftn.event_hopper.dtos.users.person.*;
import com.ftn.event_hopper.dtos.location.SimpleLocationDTO;

import java.util.List;
import java.util.stream.Collectors;

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

        // For UpdatedPersonDTO
        modelMapper.typeMap(Person.class, UpdatedPersonDTO.class)
                .addMappings(mapper -> mapper.using(locationConverter)
                        .map(Person::getLocation, UpdatedPersonDTO::setLocation));

        // For CreatedPersonDTO
        modelMapper.typeMap(Person.class, CreatedPersonDTO.class)
                .addMappings(mapper -> mapper.using(locationConverter)
                        .map(Person::getLocation, CreatedPersonDTO::setLocation));


        // For UpdatePersonDTO (with PersonType)
        modelMapper.typeMap(Person.class, UpdatePersonDTO.class)
                .addMappings(mapper -> mapper.using(locationConverter)
                        .map(Person::getLocation, UpdatePersonDTO::setLocation));

        // For ProfileInfoForPersonDTO
        modelMapper.typeMap(Person.class, ProfileForPersonDTO.class)
                .addMappings(mapper -> {
                    mapper.using(locationConverter).map(Person::getLocation, ProfileForPersonDTO::setLocation);
                    // Assuming attending events, favorite events and products are set elsewhere (e.g. by service layer)
                    mapper.map(Person::getAttendingEvents, ProfileForPersonDTO::setAttendingEvents);
                    mapper.map(Person::getFavoriteEvents, ProfileForPersonDTO::setFavoriteEvents);
                    mapper.map(Person::getFavoriteProducts, ProfileForPersonDTO::setFavoriteProducts);
                });

        // For HomepageInfoForPersonDTO
        modelMapper.typeMap(Person.class, HomepageForPersonDTO.class)
                .addMappings(mapper -> {
                    mapper.using(locationConverter).map(Person::getLocation, HomepageForPersonDTO::setLocation);
                    // Assuming favorite events and products are set elsewhere
                    mapper.map(Person::getFavoriteEvents, HomepageForPersonDTO::setFavoriteEvents);
                    mapper.map(Person::getFavoriteProducts, HomepageForPersonDTO::setFavoriteProducts);
                });
    }

    public SimplePersonDTO fromPersonToSimpleDTO(Person person) {
        return modelMapper.map(person, SimplePersonDTO.class);
    }

    public UpdatedPersonDTO fromPersonToUpdatedDTO(Person person) {
        return modelMapper.map(person, UpdatedPersonDTO.class);
    }

    public CreatedPersonDTO fromPersonToCreatedDTO(Person person) {
        return modelMapper.map(person, CreatedPersonDTO.class);
    }

    public UpdatePersonDTO fromPersonToUpdateDTO(Person person) {
        return modelMapper.map(person, UpdatePersonDTO.class);
    }

    public ProfileForPersonDTO fromPersonToProfileInfoDTO(Person person) {
        return modelMapper.map(person, ProfileForPersonDTO.class);
    }

    public HomepageForPersonDTO fromPersonToHomepageInfoDTO(Person person) {
        return modelMapper.map(person, HomepageForPersonDTO.class);
    }

    public List<SimplePersonDTO> fromPersonListToSimpleDTOList(List<Person> persons) {
        return persons.stream()
                .map(this::fromPersonToSimpleDTO)
                .collect(Collectors.toList());
    }

    public List<UpdatedPersonDTO> fromPersonListToUpdatedDTOList(List<Person> persons) {
        return persons.stream()
                .map(this::fromPersonToUpdatedDTO)
                .collect(Collectors.toList());
    }

    public Person fromCreatePersonDTOToPerson(CreatePersonDTO createPersonDTO) {
        Person person = modelMapper.map(createPersonDTO, Person.class);
        // Manually set the Location object since CreatePersonDTO contains LocationDTO
        person.setLocation(locationDTOMapper.fromLocationDTOToLocation(createPersonDTO.getLocation()));
        return person;
    }

}
