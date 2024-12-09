package com.ftn.event_hopper.services;

import com.ftn.event_hopper.mapper.user.ServiceProviderDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ftn.event_hopper.repositories.user.ServiceProviderRepository;

@Service
public class ServiceProviderService {
    @Autowired
    private ServiceProviderRepository serviceProviderRepository;
    @Autowired
    private ServiceProviderDTOMapper serviceProviderDTOMapper;


}
