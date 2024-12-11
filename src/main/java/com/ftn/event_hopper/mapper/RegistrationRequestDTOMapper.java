package com.ftn.event_hopper.mapper;


import com.ftn.event_hopper.dtos.registration.*;
import com.ftn.event_hopper.models.registration.RegistrationRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RegistrationRequestDTOMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public RegistrationRequestDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RegistrationRequestDTO fromRegistrationRequestToDTO(RegistrationRequest registrationRequest) {
        return modelMapper.map(registrationRequest, RegistrationRequestDTO.class);
    }

    public CreatedRegistrationRequestDTO fromRegistrationRequestToCreatedDTO(RegistrationRequest registrationRequest) {
        return modelMapper.map(registrationRequest, CreatedRegistrationRequestDTO.class);
    }

    public UpdatedRegistrationRequestDTO fromRegistrationRequestToUpdatedDTO(RegistrationRequest request) {
        return modelMapper.map(request, UpdatedRegistrationRequestDTO.class);
    }

    public RegistrationRequest fromDTOToRegistrationRequest(RegistrationRequestDTO requestDto) {
        return modelMapper.map(requestDto, RegistrationRequest.class);
    }

    public UpdateRegistrationRequestDTO fromRegistrationRequestToUpdateDTO(RegistrationRequestDTO requestDto) {
        return modelMapper.map(requestDto, UpdateRegistrationRequestDTO.class);
    }

    public RegistrationRequest fromCreateDTOToRegistrationRequest(CreateRegistrationRequestDTO requestDto) {
        return modelMapper.map(requestDto, RegistrationRequest.class);
    }

    public RegistrationRequest fromCreatedDTOToRegistrationRequest(CreatedRegistrationRequestDTO requestDto) {
        return modelMapper.map(requestDto, RegistrationRequest.class);
    }

    public List<RegistrationRequestDTO> fromRegistrationRequestListToDTOList(List<RegistrationRequest> requests) {
        return requests.stream()
                .map(this::fromRegistrationRequestToDTO)
                .collect(Collectors.toList());
    }


}
