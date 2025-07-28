package com.ftn.event_hopper.mapper.users;

import com.ftn.event_hopper.dtos.location.CreateLocationDTO;
import com.ftn.event_hopper.dtos.users.serviceProvider.*;
import com.ftn.event_hopper.dtos.location.LocationDTO;
import com.ftn.event_hopper.mapper.locations.LocationDTOMapper;
import com.ftn.event_hopper.models.locations.Location;
import com.ftn.event_hopper.models.users.ServiceProvider;
import com.ftn.event_hopper.services.users.AccountService;
import org.modelmapper.ModelMapper;
import org.modelmapper.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ServiceProviderDTOMapper {

    private final ModelMapper modelMapper;
    private final LocationDTOMapper locationDTOMapper;

    @Autowired
    public ServiceProviderDTOMapper(ModelMapper modelMapper, LocationDTOMapper locationDTOMapper) {
        this.modelMapper = modelMapper;
        this.locationDTOMapper = locationDTOMapper;
        configureMappings();
    }

    private void configureMappings() {
        // Custom converter for nested Location -> LocationDTO
        Converter<Location, LocationDTO> locationConverter = context ->
                locationDTOMapper.fromLocationToLocationDTO(context.getSource());

        Converter<Location, CreateLocationDTO> createLocationConverter = context ->
                locationDTOMapper.fromLocationToCreateDTO(context.getSource());


        // Custom mapping for ServiceProvider -> SimpleServiceProviderDTO
        modelMapper.typeMap(ServiceProvider.class, SimpleServiceProviderDTO.class)
                .addMappings(mapper -> {
                    mapper.using(locationConverter)
                            .map(ServiceProvider::getCompanyLocation, SimpleServiceProviderDTO::setCompanyLocation);
                });


        // Custom mapping for ServiceProvider -> CreatedServiceProviderDTO
        modelMapper.typeMap(ServiceProvider.class, CreatedServiceProviderDTO.class)
                .addMappings(mapper -> {
                    mapper.using(locationConverter)
                            .map(ServiceProvider::getCompanyLocation, CreatedServiceProviderDTO::setCompanyLocation);
                });

        // Custom mapping for ServiceProvider -> CreateServiceProviderDTO
        modelMapper.typeMap(ServiceProvider.class, CreateServiceProviderDTO.class)
                .addMappings(mapper -> {
                    mapper.using(createLocationConverter)
                            .map(ServiceProvider::getCompanyLocation, CreateServiceProviderDTO::setCompanyLocation);
                });


        // Custom mapping for ServiceProvider -> UpdatedServiceProviderDTO
        modelMapper.typeMap(ServiceProvider.class, UpdatedServiceProviderDTO.class)
                .addMappings(mapper -> {
                    mapper.using(locationConverter)
                            .map(ServiceProvider::getCompanyLocation, UpdatedServiceProviderDTO::setCompanyLocation);
                });

        // Custom mapping for ServiceProvider -> UpdateServiceProviderDTO
        modelMapper.typeMap(ServiceProvider.class, UpdateServiceProviderDTO.class)
                .addMappings(mapper -> {
                    mapper.using(locationConverter)
                            .map(ServiceProvider::getCompanyLocation, UpdateServiceProviderDTO::setCompanyLocation);
                });
    }

    public SimpleServiceProviderDTO fromServiceProviderToSimpleDTO(ServiceProvider serviceProvider) {
        return modelMapper.map(serviceProvider, SimpleServiceProviderDTO.class);
    }

    public CreateServiceProviderDTO fromServiceProviderToCreateDTO(ServiceProvider serviceProvider) {
        return modelMapper.map(serviceProvider, CreateServiceProviderDTO.class);
    }

    public CreatedServiceProviderDTO fromServiceProviderToCreatedDTO(ServiceProvider serviceProvider) {
        return modelMapper.map(serviceProvider, CreatedServiceProviderDTO.class);
    }

    public UpdateServiceProviderDTO fromServiceProviderToUpdateDTO(ServiceProvider serviceProvider) {
        return modelMapper.map(serviceProvider, UpdateServiceProviderDTO.class);
    }

    public UpdatedServiceProviderDTO fromServiceProviderToUpdatedDTO(ServiceProvider serviceProvider) {
        return modelMapper.map(serviceProvider, UpdatedServiceProviderDTO.class);
    }


    public ServiceProvider fromSimpleServiceProviderDTOToServiceProvider(SimpleServiceProviderDTO dto) {
        ServiceProvider serviceProvider = modelMapper.map(dto, ServiceProvider.class);
        serviceProvider.setCompanyLocation(locationDTOMapper.fromLocationDTOToLocation(dto.getCompanyLocation()));
        return serviceProvider;
    }

    public ServiceProvider fromCreateServiceProviderDTOToServiceProvider(CreateServiceProviderDTO dto) {
        ServiceProvider serviceProvider = modelMapper.map(dto, ServiceProvider.class);
        serviceProvider.setWorkStart(dto.getWorkStart());
        serviceProvider.setWorkEnd(dto.getWorkEnd());
        serviceProvider.setCompanyLocation(locationDTOMapper.fromCreateDTOToLocation(dto.getCompanyLocation()));
        return serviceProvider;
    }


    public List<SimpleServiceProviderDTO> fromServiceProviderListToSimpleDTOList(List<ServiceProvider> persons) {
        return persons.stream()
                .map(this::fromServiceProviderToSimpleDTO)
                .collect(Collectors.toList());
    }

    public ServiceProviderDetailsDTO fromServiceProviderToDetailsDTO(ServiceProvider provider) {
        ServiceProviderDetailsDTO detailsDTO = modelMapper.map(provider, ServiceProviderDetailsDTO.class);
        detailsDTO.setCompanyLocation(locationDTOMapper.fromLocationToLocationDTO(provider.getCompanyLocation()));
        return detailsDTO;
    }
}
