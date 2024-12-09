package com.ftn.event_hopper.mapper.solutions;

import com.ftn.event_hopper.dtos.events.SimpleEventDTO;
import com.ftn.event_hopper.dtos.events.SimpleEventTypeDTO;
import com.ftn.event_hopper.dtos.location.LocationDTO;
import com.ftn.event_hopper.dtos.solutions.SimpleProductDTO;
import com.ftn.event_hopper.mapper.LocationDTOMapper;
import com.ftn.event_hopper.models.eventTypes.EventType;
import com.ftn.event_hopper.models.locations.Location;
import com.ftn.event_hopper.models.solutions.Product;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class ProductDTOMapper {
    private final ModelMapper modelMapper;
    private final LocationDTOMapper locationDTOMapper;

    @Autowired
    public ProductDTOMapper(ModelMapper modelMapper, LocationDTOMapper locationDTOMapper) {
        this.modelMapper = modelMapper;
        this.locationDTOMapper = locationDTOMapper;
        configureMappings();
    }


    private void configureMappings() {
        // Custom converter for Location -> SimpleLocationDTO
        Converter<Location, LocationDTO> locationConverter = context ->
                locationDTOMapper.fromLocationToLocationDTO(context.getSource());

        // Custom mapping for Product -> SimpleProductDTO
        modelMapper.typeMap(Product.class, SimpleProductDTO.class);

        // Custom mapping for EventType -> SimpleEventTypeDTO (if needed)
        modelMapper.typeMap(EventType.class, SimpleEventTypeDTO.class);
    }

    public SimpleProductDTO fromProductToSimpleDTO(Product product) {
        return modelMapper.map(product, SimpleProductDTO.class);
    }

    public List<SimpleProductDTO> fromProductListToSimpleDTOList(List<Product> products) {
        return products.stream()
                .map(this::fromProductToSimpleDTO)
                .collect(Collectors.toList());
    }
}
