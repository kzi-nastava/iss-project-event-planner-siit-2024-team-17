package com.ftn.event_hopper.repositories.registrationRequests;

import com.ftn.event_hopper.models.registration.RegistrationRequest;
import com.ftn.event_hopper.models.registration.RegistrationRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest, UUID> {
    List<RegistrationRequest> findByStatus(RegistrationRequestStatus status);
}
