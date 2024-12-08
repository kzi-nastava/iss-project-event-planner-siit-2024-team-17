package com.ftn.event_hopper.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ftn.event_hopper.repositories.ServiceProviderRepository;
import com.ftn.event_hopper.mapper.ServiceProviderDTOMapper;

@Service
public class ServiceProviderService {
    @Autowired
    private ServiceProviderRepository serviceProviderRepository;
    @Autowired
    private ServiceProviderDTOMapper serviceProviderDTOMapper;


}
