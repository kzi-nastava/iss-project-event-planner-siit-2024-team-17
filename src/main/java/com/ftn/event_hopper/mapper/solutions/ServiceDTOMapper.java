package com.ftn.event_hopper.mapper.solutions;

import com.ftn.event_hopper.dtos.categories.SimpleCategoryDTO;
import com.ftn.event_hopper.dtos.solutions.*;
import com.ftn.event_hopper.mapper.categories.CategoryDTOMapper;
import com.ftn.event_hopper.models.categories.Category;
import com.ftn.event_hopper.models.solutions.Product;
import com.ftn.event_hopper.models.solutions.Service;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ServiceDTOMapper {
    private final ModelMapper modelMapper;
    private final CategoryDTOMapper categoryDTOMapper;

    public ServiceDTOMapper(ModelMapper modelMapper, CategoryDTOMapper categoryDTOMapper) {
        this.modelMapper = modelMapper;
        this.categoryDTOMapper = categoryDTOMapper;
        configureMappings();
    }

    private void configureMappings() {
        Converter<Category, SimpleCategoryDTO> productConverter = context ->
                categoryDTOMapper.fromCategoryToSimpleCategoryDTO(context.getSource());

        // Custom mapping for Product -> ProductDTO
        modelMapper.typeMap(Product.class, SimpleProductDTO.class)
                .addMappings(mapper -> mapper.using(productConverter)
                        .map(Product::getCategory, SimpleProductDTO::setCategory));
    }

    public Page<ServiceManagementDTO> fromServicePageToServiceManagementDTOPage(Page<Service> all) {
        return all.map(this::fromServiceToServiceManagementDTO);
    }

    private ServiceManagementDTO fromServiceToServiceManagementDTO(Service service) {
        return modelMapper.map(service, ServiceManagementDTO.class);
    }

    public Service fromCreateServiceDTOToService(CreateServiceDTO service) {
        return modelMapper.map(service, Service.class);
    }

    public CreatedServiceDTO fromServiceToCreatedServiceDTO(Service newService) {
        return modelMapper.map(newService, CreatedServiceDTO.class);
    }

    public UpdatedServiceDTO fromServiceToUpdatedServiceDTO(Service updated) {
        return modelMapper.map(updated, UpdatedServiceDTO.class);
    }
}
