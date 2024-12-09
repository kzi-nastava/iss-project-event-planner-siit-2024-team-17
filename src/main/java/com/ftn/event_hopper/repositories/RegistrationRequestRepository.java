package com.ftn.event_hopper.repositories;

import com.ftn.event_hopper.models.registration.RegistrationRequest;
import com.ftn.event_hopper.models.registration.RegistrationRequestStatus;
import com.ftn.event_hopper.models.users.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest, UUID> {

    RegistrationRequest findByPerson(Person person);
    List<RegistrationRequest> findByStatus(RegistrationRequestStatus status);
}
