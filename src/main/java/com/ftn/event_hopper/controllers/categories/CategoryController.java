package com.ftn.event_hopper.controllers.categories;

import com.ftn.event_hopper.dtos.categories.*;
import com.ftn.event_hopper.services.categories.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<CategoryDTO>> getCategories() {
        List<CategoryDTO> categories = categoryService.findAllApproved();

        return new ResponseEntity<Collection<CategoryDTO>>(categories, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable("id") UUID id) {
        CategoryDTO category = categoryService.findOneCategory(id);

        if (category == null) {
            return new ResponseEntity<CategoryDTO>(HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<CategoryDTO>(category, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedCategoryDTO> createCategory(@RequestBody CreateCategoryDTO category) {
        CreatedCategoryDTO createdCategory = categoryService.create(category);

        return new ResponseEntity<CreatedCategoryDTO>(createdCategory, HttpStatus.CREATED);
    }

    @PostMapping(value = "/suggestions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedCategorySuggestionDTO> suggestCategoryCreation(@RequestBody CreateCategorySuggestionDTO suggestion) {
        CreatedCategorySuggestionDTO createdCategorySuggestion = categoryService.createSuggestion(suggestion);


        return new ResponseEntity<CreatedCategorySuggestionDTO>(createdCategorySuggestion, HttpStatus.CREATED);
    }

    @GetMapping(value = "/suggestions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<CategorySuggestionDTO>> getCategoriesSuggestions() {
        Collection<CategorySuggestionDTO> suggestions =  categoryService.findAllSuggestions();

        return new ResponseEntity<Collection<CategorySuggestionDTO>>(suggestions, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCategory(@PathVariable("id") UUID id, @RequestBody UpdateCategoryDTO category) {
        try {
            UpdatedCategoryDTO updatedCategory = categoryService.updateCategory(id, category);

            return new ResponseEntity<UpdatedCategoryDTO>(updatedCategory, HttpStatus.OK);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/suggestions/{id}/approve", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> approveCategorySuggestion(@PathVariable("id") UUID id) {
        try {
        UpdatedCategorySuggestionDTO updatedCategorySuggestion = categoryService.approveCategorySuggestion(id);

        return new ResponseEntity<UpdatedCategorySuggestionDTO>(updatedCategorySuggestion, HttpStatus.OK);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/suggestions/{id}/reject/{substituteCategoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> rejectCategorySuggestion(@PathVariable("id") UUID id, @PathVariable UUID substituteCategoryId) {
        try {
            UpdatedCategorySuggestionDTO updatedCategorySuggestion = categoryService.rejectCategorySuggestion(id, substituteCategoryId);

            return new ResponseEntity<UpdatedCategorySuggestionDTO>(updatedCategorySuggestion, HttpStatus.OK);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") UUID id) {
        boolean deleted = categoryService.deleteCategory(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
