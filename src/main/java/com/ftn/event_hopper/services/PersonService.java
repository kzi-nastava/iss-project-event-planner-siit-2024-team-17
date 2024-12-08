package com.ftn.event_hopper.services;

import com.ftn.event_hopper.dtos.users.person.*;
import com.ftn.event_hopper.mapper.PersonDTOMapper;
import com.ftn.event_hopper.models.locations.Location;
import com.ftn.event_hopper.models.users.Person;
import com.ftn.event_hopper.models.users.PersonType;
import com.ftn.event_hopper.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonDTOMapper personDTOMapper;

    public SimplePersonDTO findOne(UUID id) {
        Person person = personRepository.findById(id).orElseGet(null);
        return personDTOMapper.fromPersonToSimpleDTO(person);
    }

    public List<SimplePersonDTO> findAll() {
        List<Person> persons = personRepository.findAll();
        return personDTOMapper.fromPersonListToSimpleDTOList(persons);
    }

    public List<SimplePersonDTO> findByType(PersonType type) {
        List<Person> persons = personRepository.findByType(type);
        return personDTOMapper.fromPersonListToSimpleDTOList(persons);
    }

    public CreatedPersonDTO create(CreatePersonDTO personDTO){
        Person person = personDTOMapper.fromCreatePersonDTOToPerson(personDTO);
        this.save(person);
        return personDTOMapper.fromPersonToCreatedDTO(person);
    }

    public UpdatedPersonDTO update(UUID id, UpdatePersonDTO personDTO){
        Person person = personRepository.findById(id).orElseGet(null);
        if(person!= null){
            person.setName(personDTO.getName());
            person.setSurname(personDTO.getSurname());
            person.setProfilePicture(personDTO.getProfilePicture());
            person.setPhoneNumber(personDTO.getPhoneNumber());
            person.setType(personDTO.getType());
            Location location = person.getLocation();
            location.setCity(personDTO.getLocation().getCity());
            location.setAddress(personDTO.getLocation().getAddress());
            this.save(person);
        }
        return personDTOMapper.fromPersonToUpdatedDTO(person);
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public HomepageForPersonDTO getHomepage(UUID id){
        Person person = personRepository.findById(id).orElseGet(null);
        return personDTOMapper.fromPersonToHomepageInfoDTO(person);
    }

    public ProfileForPersonDTO getProfile(UUID id){
        Person person = personRepository.findById(id).orElseGet(null);
        return personDTOMapper.fromPersonToProfileInfoDTO(person);
    }

    // person doesnt have remove/delete
}
