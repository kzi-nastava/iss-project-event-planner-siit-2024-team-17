package com.ftn.event_hopper.mapper.categories;

import com.ftn.event_hopper.dtos.categories.*;
import com.ftn.event_hopper.dtos.eventTypes.SimpleEventTypeDTO;
import com.ftn.event_hopper.mapper.eventTypes.EventTypeDTOMapper;
import com.ftn.event_hopper.models.categories.Category;
import com.ftn.event_hopper.models.eventTypes.EventType;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CategoryDTOMapper {
    private final ModelMapper modelMapper;
    private final EventTypeDTOMapper eventTypeDTOMapper;

    @Autowired
    public CategoryDTOMapper(ModelMapper modelMapper, EventTypeDTOMapper eventTypeDTOMapper) {
        this.modelMapper = modelMapper;
        this.eventTypeDTOMapper = eventTypeDTOMapper;
        configureMappings();
    }

    private void configureMappings() {
        Converter<Set<EventType>, Set<SimpleEventTypeDTO>> eventTypeConverter = context ->
                context.getSource().stream()
                        .map(eventTypeDTOMapper::fromEventTypeToSimpleDTO)
                        .collect(Collectors.toSet());

        modelMapper.typeMap(Category.class, CategoryDTO.class)
                .addMappings(mapper -> mapper.using(eventTypeConverter)
                        .map(Category::getEventTypes, CategoryDTO::setEventTypes));

    }

    public CategoryDTO fromCategoryToCategoryDTO(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }

    public List<CategoryDTO> fromCategoryListToCategoryDTOList(List<Category> categories) {
        return categories.stream()
                .map(this::fromCategoryToCategoryDTO)
                .collect(Collectors.toList());
    }

    public Category fromCreateCategoryDTOToCategory(CreateCategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class);
    }

    public CreatedCategoryDTO fromCategoryToCreatedCategoryDTO(Category created) {
        return modelMapper.map(created, CreatedCategoryDTO.class);
    }

    public Category fromCreateCategorySuggestionDTOToCategory(CreateCategorySuggestionDTO suggestion) {
        return modelMapper.map(suggestion, Category.class);
    }

    public CreatedCategorySuggestionDTO fromCategoryToCreatedCategorySuggestionDTO(Category created) {
        return modelMapper.map(created, CreatedCategorySuggestionDTO.class);
    }

    public Collection<CategorySuggestionDTO> fromCategoryListToCategorySuggestionDTOList(List<Category> categories) {
        return categories.stream()
                .map(this::fromCategoryToCategorySuggestionDTO)
                .collect(Collectors.toList());
    }

    public List<SimpleCategoryDTO> fromCategoryListToSimpleDTOList(List<Category> categories) {
        return categories.stream()
                .map(this::fromCategoryToSimpleCategoryDTO)
                .collect(Collectors.toList());
    }

    private CategorySuggestionDTO fromCategoryToCategorySuggestionDTO(Category category) {
        return modelMapper.map(category, CategorySuggestionDTO.class);
    }

    public UpdatedCategoryDTO fromCategoryToUpdatedCategoryDTO(Category updated) {
        return modelMapper.map(updated, UpdatedCategoryDTO.class);
    }

    public UpdatedCategorySuggestionDTO fromCategoryToUpdatedCategorySuggestionDTO(Category updated) {
        return modelMapper.map(updated, UpdatedCategorySuggestionDTO.class);
    }

    public SimpleCategoryDTO fromCategoryToSimpleCategoryDTO(Category category) {
        return modelMapper.map(category, SimpleCategoryDTO.class);
    }
}
