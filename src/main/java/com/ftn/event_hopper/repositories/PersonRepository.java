package com.ftn.event_hopper.repositories;

import com.ftn.event_hopper.models.users.Person;
import com.ftn.event_hopper.models.users.PersonType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {
    Person findByType(PersonType type);
}
