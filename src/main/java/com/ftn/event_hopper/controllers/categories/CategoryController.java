package com.ftn.event_hopper.controllers.categories;

import com.ftn.event_hopper.dtos.categories.*;
import com.ftn.event_hopper.models.shared.CategoryStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetCategoryDTO>> getCategories() {
        Collection<GetCategoryDTO> categories = new ArrayList<>() ;

        GetCategoryDTO category = new GetCategoryDTO();

        category.setId(UUID.randomUUID());
        category.setName("Category 1");
        category.setDescription("Description 1");
        category.setStatus(CategoryStatus.APPROVED);
        category.setEventTypesIds(new ArrayList<UUID>() {{
            add(UUID.randomUUID());
            add(UUID.randomUUID());
        }});

        GetCategoryDTO category2 = new GetCategoryDTO();

        category2.setId(UUID.randomUUID());
        category2.setName("Category 2");
        category2.setDescription("Description 2");
        category2.setStatus(CategoryStatus.PENDING);
        category2.setEventTypesIds(new ArrayList<UUID>() {{
            add(UUID.randomUUID());
            add(UUID.randomUUID());
        }});

        categories.add(category);
        categories.add(category2);

        return new ResponseEntity<Collection<GetCategoryDTO>>(categories, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetCategoryDTO> getCategory(@PathVariable("id") UUID id) {
        GetCategoryDTO category = new GetCategoryDTO();

        if (category == null) {
            return new ResponseEntity<GetCategoryDTO>(HttpStatus.NOT_FOUND);
        }

        category.setId(id);
        category.setName("Category 1");
        category.setDescription("Description 1");
        category.setStatus(CategoryStatus.APPROVED);
        category.setEventTypesIds(new ArrayList<UUID>() {{
            add(UUID.randomUUID());
            add(UUID.randomUUID());
        }});

        return new ResponseEntity<GetCategoryDTO>(category, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedCategoryDTO> createCategory(@RequestBody CreateCategoryDTO category) {
        CreatedCategoryDTO createdCategory = new CreatedCategoryDTO();

        createdCategory.setId(UUID.randomUUID());
        createdCategory.setName(category.getName());
        createdCategory.setDescription(category.getDescription());
        createdCategory.setStatus(CategoryStatus.APPROVED);
        createdCategory.setEventTypesIds(new ArrayList<UUID>());

        return new ResponseEntity<CreatedCategoryDTO>(createdCategory, HttpStatus.CREATED);
    }

    @PostMapping(value = "/suggestions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedCategoryDTO> suggestCategoryCreation(@RequestBody CreateCategoryDTO category) {
        CreatedCategoryDTO createdCategory = new CreatedCategoryDTO();

        createdCategory.setId(UUID.randomUUID());
        createdCategory.setName(category.getName());
        createdCategory.setDescription(category.getDescription());
        createdCategory.setStatus(CategoryStatus.PENDING);
        createdCategory.setEventTypesIds(new ArrayList<UUID>());

        return new ResponseEntity<CreatedCategoryDTO>(createdCategory, HttpStatus.CREATED);
    }

    @GetMapping(value = "/suggestions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetCategoryDTO>> getCategoriesSuggestions() {
        Collection<GetCategoryDTO> categories = new ArrayList<>() ;

        GetCategoryDTO category = new GetCategoryDTO();

        category.setId(UUID.randomUUID());
        category.setName("Category 1");
        category.setDescription("Description 1");
        category.setStatus(CategoryStatus.PENDING);
        category.setEventTypesIds(new ArrayList<UUID>() {{
            add(UUID.randomUUID());
            add(UUID.randomUUID());
        }});

        GetCategoryDTO category2 = new GetCategoryDTO();

        category2.setId(UUID.randomUUID());
        category2.setName("Category 2");
        category2.setDescription("Description 2");
        category2.setStatus(CategoryStatus.PENDING);
        category2.setEventTypesIds(new ArrayList<UUID>() {{
            add(UUID.randomUUID());
            add(UUID.randomUUID());
        }});

        categories.add(category);
        categories.add(category2);

        return new ResponseEntity<Collection<GetCategoryDTO>>(categories, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedCategoryDTO> updateCategory(@PathVariable("id") UUID id, @RequestBody UpdateCategoryDTO category) {
        UpdatedCategoryDTO updatedCategory = new UpdatedCategoryDTO();

        updatedCategory.setId(id);
        updatedCategory.setName(category.getName());
        updatedCategory.setDescription(category.getDescription());
        updatedCategory.setStatus(category.getStatus());
        updatedCategory.setEventTypesIds(new ArrayList<UUID>());

        return new ResponseEntity<UpdatedCategoryDTO>(updatedCategory, HttpStatus.OK);
    }
}
