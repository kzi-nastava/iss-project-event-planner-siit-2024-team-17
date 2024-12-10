package com.ftn.event_hopper.mapper.user;

import com.ftn.event_hopper.dtos.events.SimpleEventDTO;
import com.ftn.event_hopper.dtos.solutions.SimpleProductDTO;
import com.ftn.event_hopper.mapper.EventDTOMapper;
import com.ftn.event_hopper.mapper.LocationDTOMapper;
import com.ftn.event_hopper.mapper.solutions.ProductDTOMapper;
import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.solutions.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.hibernate.collection.spi.PersistentSet;


import com.ftn.event_hopper.models.users.Person;
import com.ftn.event_hopper.models.locations.Location;
import com.ftn.event_hopper.dtos.users.person.*;
import com.ftn.event_hopper.dtos.location.SimpleLocationDTO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PersonDTOMapper {

    private final ModelMapper modelMapper;
    private final LocationDTOMapper locationDTOMapper;
    private final EventDTOMapper eventDTOMapper;
    private final ProductDTOMapper productDTOMapper;

    @Autowired
    public PersonDTOMapper(ModelMapper modelMapper, LocationDTOMapper locationDTOMapper, EventDTOMapper eventDTOMapper, ProductDTOMapper productDTOMapper) {
        this.modelMapper = modelMapper;
        this.locationDTOMapper = locationDTOMapper;
        this.eventDTOMapper = eventDTOMapper;
        this.productDTOMapper = productDTOMapper;
        configureMappings();
    }

    private void configureMappings() {
        // Custom converter for nested Location to SimpleLocationDTO
        Converter<Location, SimpleLocationDTO> locationConverter = context ->
                locationDTOMapper.fromLocationToSimpleDTO(context.getSource());

        Converter<Event, SimpleEventDTO> eventConverter = context ->
                eventDTOMapper.fromEventToSimpleDTO(context.getSource());

        Converter<Product, SimpleProductDTO> productConverter = context ->
                productDTOMapper.fromProductToSimpleDTO(context.getSource());


        Converter<Set<Event>, Set<SimpleEventDTO>> eventSetConverter = context ->
                context.getSource().stream()
                        .map(eventDTOMapper::fromEventToSimpleDTO)
                        .collect(Collectors.toSet());

        Converter<Set<Product>, Set<SimpleProductDTO>> productSetConverter = context ->
                context.getSource().stream()
                        .map(productDTOMapper::fromProductToSimpleDTO)
                        .collect(Collectors.toSet());


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

        // For ProfileForPersonDTO (attending and favorite events mapped)
        modelMapper.typeMap(Person.class, ProfileForPersonDTO.class)
                .addMappings(mapper -> {
                    mapper.using(locationConverter).map(Person::getLocation, ProfileForPersonDTO::setLocation);
                    mapper.using(eventSetConverter).map(Person::getFavoriteEvents, ProfileForPersonDTO::setFavoriteEvents);
                    mapper.using(eventSetConverter).map(Person::getAttendingEvents, ProfileForPersonDTO::setAttendingEvents);
                    mapper.using(productSetConverter).map(Person::getFavoriteProducts, ProfileForPersonDTO::setFavoriteProducts);
                });

        // For HomepageForPersonDTO (attending and favorite events mapped)
        modelMapper.typeMap(Person.class, HomepageForPersonDTO.class)
                .addMappings(mapper -> {
                    mapper.using(locationConverter).map(Person::getLocation, HomepageForPersonDTO::setLocation);
                    mapper.using(eventSetConverter).map(Person::getFavoriteEvents, HomepageForPersonDTO::setFavoriteEvents);
                    mapper.using(productSetConverter).map(Person::getFavoriteProducts, HomepageForPersonDTO::setFavoriteProducts);
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

    public ProfileForPersonDTO fromPersonToProfileInfoDTO(Person person) {
        // Mapping attending and favorite events to SimpleEventDTOs
        ProfileForPersonDTO dto = modelMapper.map(person, ProfileForPersonDTO.class);
        dto.setAttendingEvents(person.getAttendingEvents().stream()
                .map(eventDTOMapper::fromEventToSimpleDTO)
                .collect(Collectors.toSet()));
        dto.setFavoriteEvents(person.getFavoriteEvents().stream()
                .map(eventDTOMapper::fromEventToSimpleDTO)
                .collect(Collectors.toSet()));
        return dto;
    }

    public HomepageForPersonDTO fromPersonToHomepageInfoDTO(Person person) {
        // Mapping favorite events to SimpleEventDTOs
        HomepageForPersonDTO dto = modelMapper.map(person, HomepageForPersonDTO.class);
        dto.setFavoriteEvents(person.getFavoriteEvents().stream()
                .map(eventDTOMapper::fromEventToSimpleDTO)
                .collect(Collectors.toSet()));
        return dto;
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
        person.setLocation(locationDTOMapper.fromSimpleLocationDTOToLocation(createPersonDTO.getLocation()));
        return person;
    }

    public Person fromSimplePersonDTOToPerson(SimplePersonDTO simplePersonDTO) {
        Person person = modelMapper.map(simplePersonDTO, Person.class);
        // Manually set the Location object since CreatePersonDTO contains LocationDTO
        person.setLocation(locationDTOMapper.fromSimpleLocationDTOToLocation(simplePersonDTO.getLocation()));
        return person;
    }
}
