package com.ftn.event_hopper.services.categories;

import com.ftn.event_hopper.dtos.categories.*;
import com.ftn.event_hopper.mapper.categories.CategoryDTOMapper;
import com.ftn.event_hopper.mapper.solutions.ProductDTOMapper;
import com.ftn.event_hopper.models.categories.Category;
import com.ftn.event_hopper.models.eventTypes.EventType;
import com.ftn.event_hopper.models.shared.CategoryStatus;
import com.ftn.event_hopper.models.solutions.Product;
import com.ftn.event_hopper.repositories.categoies.CategoryRepository;
import com.ftn.event_hopper.repositories.eventTypes.EventTypeRepository;
import com.ftn.event_hopper.repositories.solutions.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryDTOMapper categoryMapper;

    @Autowired
    private ProductDTOMapper productMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EventTypeRepository eventTypeRepository;

    public List<CategoryDTO> findAllApproved() {
        List<Category> categories = categoryRepository.findByStatusAndIsDeletedFalse(CategoryStatus.APPROVED);
        List<CategoryDTO> categoryDTOs = categoryMapper.fromCategoryListToCategoryDTOList(categories);

        for (CategoryDTO categoryDTO : categoryDTOs) {
            boolean hasProducts = productRepository.existsByCategory_Id(categoryDTO.getId());
            categoryDTO.setDeletable(!hasProducts);
        }

        return categoryDTOs;
    }

    public CategoryDTO findOneCategory(UUID id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return null;
        }
        return categoryMapper.fromCategoryToCategoryDTO(category);
    }

    public CreatedCategoryDTO create(CreateCategoryDTO categoryDTO) {
        Category category = categoryMapper.fromCreateCategoryDTOToCategory(categoryDTO);
        category.setStatus(CategoryStatus.APPROVED);
        category.setDeleted(false);
        Category created = categoryRepository.save(category);
        categoryRepository.flush();
        return categoryMapper.fromCategoryToCreatedCategoryDTO(created);
    }

    public CreatedCategorySuggestionDTO createSuggestion(CreateCategorySuggestionDTO suggestion) {
        Category category = categoryMapper.fromCreateCategorySuggestionDTOToCategory(suggestion);
        category.setStatus(CategoryStatus.PENDING);
        category.setDescription("Suggested category");
        category.setDeleted(false);
        Category created = categoryRepository.save(category);
        categoryRepository.flush();
        return categoryMapper.fromCategoryToCreatedCategorySuggestionDTO(created);
    }

    public Collection<CategorySuggestionDTO> findAllSuggestions() {
        List<Category> categories = categoryRepository.findByStatus(CategoryStatus.PENDING);
        Collection<CategorySuggestionDTO> suggestions = categoryMapper.fromCategoryListToCategorySuggestionDTOList(categories);

        for (CategorySuggestionDTO suggestion : suggestions) {
            Product product = productRepository.findByCategory_Id(suggestion.getId());
            suggestion.setProduct(productMapper.fromProductToSimpleDTO(product));
        }

        return suggestions;
    }

    public UpdatedCategoryDTO updateCategory(UUID id, UpdateCategoryDTO category) {
        Category existing = categoryRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }

        existing.setName(category.getName());
        existing.setDescription(category.getDescription());
        existing.setStatus(category.getStatus());
        existing.getEventTypes().clear();
        for (UUID eventTypeId : category.getEventTypesIds()) {
            EventType eventType = eventTypeRepository.findById(eventTypeId).orElse(null);
            if (eventType != null) {
                existing.getEventTypes().add(eventType);
            }
        }

        Category updated = categoryRepository.save(existing);
        categoryRepository.flush();
        return categoryMapper.fromCategoryToUpdatedCategoryDTO(updated);
    }

    public UpdatedCategorySuggestionDTO updateCategorySuggestion(UUID id, UpdateCategorySuggestionDTO suggestion) {
        Category existing = categoryRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }

        existing.setStatus(suggestion.getStatus());

        Category updated = categoryRepository.save(existing);
        categoryRepository.flush();
        return categoryMapper.fromCategoryToUpdatedCategorySuggestionDTO(updated);
    }

    public boolean deleteCategory(UUID id) {
        Category existing = categoryRepository.findById(id).orElse(null);
        if (existing == null || existing.isDeleted() || productRepository.existsByCategory_Id(id)) {
            return false;
        }

        existing.setDeleted(true);
        existing.setEventTypes(null);
        categoryRepository.save(existing);
        categoryRepository.flush();
        return true;
    }
}
