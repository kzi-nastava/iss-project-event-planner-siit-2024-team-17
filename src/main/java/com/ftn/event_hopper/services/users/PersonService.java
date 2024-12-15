package com.ftn.event_hopper.services.users;

import com.ftn.event_hopper.dtos.users.person.*;
import com.ftn.event_hopper.mapper.users.PersonDTOMapper;
import com.ftn.event_hopper.models.locations.Location;
import com.ftn.event_hopper.models.solutions.Product;
import com.ftn.event_hopper.models.solutions.Service;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.models.users.Person;
import com.ftn.event_hopper.models.users.PersonType;
import com.ftn.event_hopper.repositories.solutions.ProductRepository;
import com.ftn.event_hopper.repositories.solutions.ServiceRepository;
import com.ftn.event_hopper.repositories.users.AccountRepository;
import com.ftn.event_hopper.repositories.users.PersonRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private PersonDTOMapper personDTOMapper;

    public SimplePersonDTO findOne(UUID id) {
        Person person = personRepository.findById(id).orElseGet(null);

        Hibernate.initialize(person.getFavoriteEvents());
        Hibernate.initialize(person.getAttendingEvents());
        Hibernate.initialize(person.getFavoriteProducts());
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
            person.setLocation(location);
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

    public void addFavoriteSolution(UUID accountId, UUID solutionId) {
        Account account = accountRepository.findById(accountId).orElseGet(null);
        Person person = account.getPerson();
        Product solution = productRepository.findById(solutionId).orElseGet(null);
        if (solution instanceof Service service) {
            service = serviceRepository.findById(solutionId).orElseGet(null);
            if (person.getFavoriteProducts().contains(service)) {
                return;
            }
            person.getFavoriteProducts()
                    .add(service);
            personRepository.save(person);
            personRepository.flush();
        } else {
            if (account.getPerson().getFavoriteProducts().contains(solution)) {
                return;
            }
            person.getFavoriteProducts()
                    .add(solution);
            personRepository.save(person);
            personRepository.flush();
        }
    }

    public void removeFavoriteSolution(UUID accountId, UUID solutionId) {
        Account account = accountRepository.findById(accountId).orElseGet(null);
        account.getPerson()
                .getFavoriteProducts()
                .removeIf(product -> product.getId().equals(solutionId));
    }


    // person doesnt have remove/delete
}
