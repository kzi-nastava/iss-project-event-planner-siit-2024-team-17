package com.ftn.event_hopper.mapper.user;

import com.ftn.event_hopper.dtos.users.person.SimplePersonDTO;
import com.ftn.event_hopper.dtos.users.serviceProvider.SimpleServiceProviderDTO;
import com.ftn.event_hopper.dtos.location.LocationDTO;
import com.ftn.event_hopper.mapper.LocationDTOMapper;
import com.ftn.event_hopper.models.locations.Location;
import com.ftn.event_hopper.models.users.Person;
import com.ftn.event_hopper.models.users.ServiceProvider;
import org.modelmapper.ModelMapper;
import org.modelmapper.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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


        // Custom mapping for ServiceProvider -> SimpleServiceProviderDTO
        modelMapper.typeMap(ServiceProvider.class, SimpleServiceProviderDTO.class)
                .addMappings(mapper -> {
                    mapper.map(ServiceProvider::getCompanyName, SimpleServiceProviderDTO::setCompanyName);
                    mapper.map(ServiceProvider::getCompanyEmail, SimpleServiceProviderDTO::setCompanyEmail);
                    mapper.map(ServiceProvider::getCompanyDescription, SimpleServiceProviderDTO::setCompanyDescription);
                    mapper.map(ServiceProvider::getCompanyPhotos, SimpleServiceProviderDTO::setCompanyPhotos);
                    mapper.map(ServiceProvider::getWorkStart, SimpleServiceProviderDTO::setWorkStart);
                    mapper.map(ServiceProvider::getWorkEnd, SimpleServiceProviderDTO::setWorkEnd);
                    mapper.using(locationConverter)
                            .map(ServiceProvider::getCompanyLocation, SimpleServiceProviderDTO::setCompanyLocation);
                });
    }

    public SimpleServiceProviderDTO fromServiceProviderToSimpleDTO(ServiceProvider serviceProvider) {
        return modelMapper.map(serviceProvider, SimpleServiceProviderDTO.class);
    }

    public ServiceProvider fromSimpleServiceProviderDTOToServiceProvider(SimpleServiceProviderDTO dto) {
        ServiceProvider serviceProvider = modelMapper.map(dto, ServiceProvider.class);
        serviceProvider.setCompanyLocation(locationDTOMapper.fromLocationDTOToLocation(dto.getCompanyLocation()));
        return serviceProvider;
    }


    public List<SimpleServiceProviderDTO> fromServiceProviderListToSimpleDTOList(List<ServiceProvider> persons) {
        return persons.stream()
                .map(this::fromServiceProviderToSimpleDTO)
                .collect(Collectors.toList());
    }
}
