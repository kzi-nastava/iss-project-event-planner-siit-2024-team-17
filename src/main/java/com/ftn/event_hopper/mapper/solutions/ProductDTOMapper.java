package com.ftn.event_hopper.mapper.solutions;

import com.ftn.event_hopper.dtos.categories.SimpleCategoryDTO;
import com.ftn.event_hopper.dtos.comments.SimpleCommentDTO;
import com.ftn.event_hopper.dtos.solutions.SimpleProductDTO;
import com.ftn.event_hopper.dtos.solutions.SolutionDetailsDTO;
import com.ftn.event_hopper.mapper.categories.CategoryDTOMapper;
import com.ftn.event_hopper.mapper.comments.CommentDTOMapper;
import com.ftn.event_hopper.models.categories.Category;
import com.ftn.event_hopper.models.comments.Comment;
import com.ftn.event_hopper.models.solutions.Product;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
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

    public List<SimpleProductDTO> fromProductListToSimpleDTOList(List<Product> products) {
        return products.stream()
                .map(this::fromProductToSimpleDTO)
                .collect(Collectors.toList());
    }

    public Page<SimpleProductDTO> fromProductPageToSimpleProductDTOPage(Page<Product> all) {
        return all.map(this::fromProductToSimpleDTO);
    }

    public SolutionDetailsDTO fromProductToSolutionDetailsDTO(Product product) {
        return modelMapper.map(product, SolutionDetailsDTO.class);
    }
}
