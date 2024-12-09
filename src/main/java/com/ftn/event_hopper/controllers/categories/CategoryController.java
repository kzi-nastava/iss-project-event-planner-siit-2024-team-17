package com.ftn.event_hopper.controllers.categories;

import com.ftn.event_hopper.dtos.categories.*;
import com.ftn.event_hopper.services.categories.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
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
    public ResponseEntity<UpdatedCategoryDTO> updateCategory(@PathVariable("id") UUID id, @RequestBody UpdateCategoryDTO category) {
        UpdatedCategoryDTO updatedCategory = categoryService.updateCategory(id, category);

        return new ResponseEntity<UpdatedCategoryDTO>(updatedCategory, HttpStatus.OK);
    }

    @PutMapping(value = "/suggestions/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedCategorySuggestionDTO> updateCategorySuggestion(@PathVariable("id") UUID id, @RequestBody UpdateCategorySuggestionDTO suggestion) {
        UpdatedCategorySuggestionDTO updatedCategorySuggestion = categoryService.updateCategorySuggestion(id, suggestion);

        return new ResponseEntity<UpdatedCategorySuggestionDTO>(updatedCategorySuggestion, HttpStatus.OK);
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
