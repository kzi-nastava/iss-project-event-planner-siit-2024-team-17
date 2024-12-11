package com.ftn.event_hopper.services;

import com.ftn.event_hopper.dtos.registration.*;
import com.ftn.event_hopper.mapper.RegistrationRequestDTOMapper;
import com.ftn.event_hopper.models.registration.RegistrationRequest;
import com.ftn.event_hopper.models.registration.RegistrationRequestStatus;
import com.ftn.event_hopper.repositories.RegistrationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RegistrationRequestService {
    @Autowired
    private RegistrationRequestRepository registrationRequestRepository;
    @Autowired
    private RegistrationRequestDTOMapper registrationRequestDTOMapper;

    public RegistrationRequestDTO findOne(UUID id) {
        RegistrationRequest request = registrationRequestRepository.findById(id).orElseGet(null);
        return registrationRequestDTOMapper.fromRegistrationRequestToDTO(request);
    }

    public List<RegistrationRequestDTO> findAll() {
        List<RegistrationRequest> requests = registrationRequestRepository.findAll();
        return registrationRequestDTOMapper.fromRegistrationRequestListToDTOList(requests);
    }

    public List<RegistrationRequestDTO> findAllByStatus(RegistrationRequestStatus status) {
        List<RegistrationRequest> requests = registrationRequestRepository.findByStatus(status);
        return registrationRequestDTOMapper.fromRegistrationRequestListToDTOList(requests);
    }


    public RegistrationRequest save(RegistrationRequest request) {
        return registrationRequestRepository.save(request);
    }

    public UpdatedRegistrationRequestDTO update(UUID id, UpdateRegistrationRequestDTO requestDTO) {
        RegistrationRequest request = registrationRequestRepository.findById(id).orElseGet(null);
        if(request!= null) {
            request.setStatus(requestDTO.getStatus());
            this.save(request);
        }
        return registrationRequestDTOMapper.fromRegistrationRequestToUpdatedDTO(request);
    }

    public CreatedRegistrationRequestDTO create(){
        RegistrationRequest request = new RegistrationRequest();
        request.setTimestamp(LocalDateTime.now());
        request.setStatus(RegistrationRequestStatus.PENDING);
        this.save(request);
        return registrationRequestDTOMapper.fromRegistrationRequestToCreatedDTO(request);
    }


    public CreatedRegistrationRequestDTO create(CreateRegistrationRequestDTO requestDTO){
        RegistrationRequest request = new RegistrationRequest();
        request.setTimestamp(LocalDateTime.now());
        request.setStatus(RegistrationRequestStatus.PENDING);
        return registrationRequestDTOMapper.fromRegistrationRequestToCreatedDTO(request);
    }

}
