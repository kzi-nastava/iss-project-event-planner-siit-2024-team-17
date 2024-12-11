package com.ftn.event_hopper.mapper.solutions;

import com.ftn.event_hopper.dtos.categories.SimpleCategoryDTO;
import com.ftn.event_hopper.dtos.solutions.SimpleProductDTO;
import com.ftn.event_hopper.mapper.categories.CategoryDTOMapper;
import com.ftn.event_hopper.models.categories.Category;
import com.ftn.event_hopper.models.solutions.Product;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductDTOMapper {
    private final ModelMapper modelMapper;
    private final CategoryDTOMapper categoryDTOMapper;

    public ProductDTOMapper(ModelMapper modelMapper, CategoryDTOMapper categoryDTOMapper) {
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

    public SimpleProductDTO fromProductToSimpleDTO(Product product) {
        return modelMapper.map(product, SimpleProductDTO.class);
    }

    public List<SimpleProductDTO> fromProductListToSimpleDTOList(List<Product> products) {
        return products.stream()
                .map(this::fromProductToSimpleDTO)
                .collect(Collectors.toList());
    }

    public Page<SimpleProductDTO> fromProductPageToSimpleProductDTOPage(Page<Product> all) {
        return all.map(this::fromProductToSimpleDTO);
    }

}
