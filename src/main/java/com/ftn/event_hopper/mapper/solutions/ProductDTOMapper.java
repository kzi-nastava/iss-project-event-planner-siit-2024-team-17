package com.ftn.event_hopper.mapper.solutions;

import com.ftn.event_hopper.dtos.categories.SimpleCategoryDTO;
import com.ftn.event_hopper.dtos.comments.SimpleCommentDTO;
import com.ftn.event_hopper.dtos.eventTypes.SimpleEventTypeDTO;
import com.ftn.event_hopper.dtos.solutions.*;
import com.ftn.event_hopper.mapper.categories.CategoryDTOMapper;
import com.ftn.event_hopper.mapper.comments.CommentDTOMapper;
import com.ftn.event_hopper.models.categories.Category;
import com.ftn.event_hopper.models.comments.Comment;
import com.ftn.event_hopper.models.solutions.Product;
import com.ftn.event_hopper.models.solutions.Service;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProductDTOMapper {
    private final ModelMapper modelMapper;
    private final CategoryDTOMapper categoryDTOMapper;
    private final CommentDTOMapper commentDTOMapper;


    public ProductDTOMapper(ModelMapper modelMapper, CategoryDTOMapper categoryDTOMapper, CommentDTOMapper commentDTOMapper) {
        this.modelMapper = modelMapper;
        this.categoryDTOMapper = categoryDTOMapper;
        this.commentDTOMapper = commentDTOMapper;
        configureMappings();
    }

    private void configureMappings() {
        Converter<Category, SimpleCategoryDTO> productConverter = context ->
                categoryDTOMapper.fromCategoryToSimpleCategoryDTO(context.getSource());

        Converter<List<Comment>, Collection<SimpleCommentDTO>> commentConverter = context ->
                commentDTOMapper.fromCommentListToSimplecommentDTOCollection(context.getSource());

        // Custom mapping for Product -> SolutionDetailsDTO
        modelMapper.typeMap(Product.class, SolutionDetailsDTO.class)
                .addMappings(mapper -> mapper.using(productConverter)
                        .map(Product::getCategory, SolutionDetailsDTO::setCategory))
                .addMappings(mapper -> mapper.using(commentConverter)
                        .map(Product::getComments, SolutionDetailsDTO::setComments));

    }

    public SimpleProductDTO fromProductToSimpleDTO(Product product) {
        return modelMapper.map(product, SimpleProductDTO.class);
    }

    public ProductForManagementDTO fromProductToProductForManagementDTO(Product product) {
        ProductForManagementDTO productMapped = modelMapper.map(product, ProductForManagementDTO.class);
        List<SimpleEventTypeDTO> eventTypes = new ArrayList<>(
                product.getEventTypes().stream()
                        .map(eventType -> modelMapper.map(eventType, SimpleEventTypeDTO.class))
                        .collect(Collectors.toList())
        );
        productMapped.setEventTypes(eventTypes);

        return productMapped;
    }

    public List<SimpleProductDTO> fromProductListToSimpleDTOList(List<Product> products) {
        return products.stream()
                .map(this::fromProductToSimpleDTO)
                .collect(Collectors.toList());
    }

    public Page<SimpleProductDTO> fromProductPageToSimpleProductDTOPage(Page<Product> all) {
        return all.map(this::fromProductToSimpleDTO);
    }

    public Page<ProductForManagementDTO> fromProductPageToProductForManagementDTOPage(Page<Product> all) {
        return all.map(this::fromProductToProductForManagementDTO);
    }


    public SolutionDetailsDTO fromProductToSolutionDetailsDTO(Product product) {
        return modelMapper.map(product, SolutionDetailsDTO.class);
    }

    public Product fromCreateProductDTOToProduct(CreateProductDTO product) {
        return modelMapper.map(product, Product.class);
    }

    public CreatedProductDTO fromProductToCreatedProductDTO(Product newProduct) {
        return modelMapper.map(newProduct, CreatedProductDTO.class);
    }

    public UpdatedProductDTO fromProductToUpdatedProductDTO(Product newProduct) {
        return modelMapper.map(newProduct, UpdatedProductDTO.class);
    }
}
