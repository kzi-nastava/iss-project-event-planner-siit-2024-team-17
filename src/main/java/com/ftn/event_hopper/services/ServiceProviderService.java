package com.ftn.event_hopper.services;

import com.ftn.event_hopper.dtos.users.person.SimplePersonDTO;
import com.ftn.event_hopper.dtos.users.serviceProvider.SimpleServiceProviderDTO;
import com.ftn.event_hopper.mapper.user.ServiceProviderDTOMapper;
import com.ftn.event_hopper.models.users.Person;
import com.ftn.event_hopper.models.users.ServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ftn.event_hopper.repositories.user.ServiceProviderRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ServiceProviderService {
    @Autowired
    private ServiceProviderRepository serviceProviderRepository;
    @Autowired
    private ServiceProviderDTOMapper serviceProviderDTOMapper;

    public SimpleServiceProviderDTO findOne(UUID id) {
        ServiceProvider serviceProvider = serviceProviderRepository.findById(id).orElseGet(null);
        return serviceProviderDTOMapper.fromServiceProviderToSimpleDTO(serviceProvider);
    }

    public List<SimpleServiceProviderDTO> findAll() {
        List<ServiceProvider> serviceProviders = serviceProviderRepository.findAll();
        return serviceProviderDTOMapper.fromServiceProviderListToSimpleDTOList(serviceProviders);
    }


}
