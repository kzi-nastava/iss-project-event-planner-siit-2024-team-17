package com.ftn.event_hopper.services;


import com.ftn.event_hopper.dtos.users.person.SimplePersonDTO;
import com.ftn.event_hopper.mapper.PersonDTOMapper;
import com.ftn.event_hopper.models.users.Person;
import com.ftn.event_hopper.models.users.PersonType;
import com.ftn.event_hopper.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    private PersonDTOMapper personDTOMapper;

    public Person findOne(UUID id) {
        return personRepository.findById(id).orElseGet(null);
    }

    public List<SimplePersonDTO> findAll() {
        List<Person> persons = personRepository.findAll();

        // Map the list of Person entities to a list of SimplePersonDTOs
        return persons.stream()
                .map(personDTOMapper::fromPersonToDTO)
                .collect(Collectors.toList());
    }

    public Page<Person> findAll(Pageable page) {
        return personRepository.findAll(page);
    }

    public Person findByType(PersonType type) {
        return personRepository.findByType(type);
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }
}
