package com.ftn.event_hopper.dtos.solutions;

import com.ftn.event_hopper.dtos.categories.SimpleCategoryDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class SimpleProductDTO {
    private UUID id;
    private String name;
    private String description;
    private List<String> pictures;
    private SimpleCategoryDTO category;
}