package com.ftn.event_hopper.dtos.categories;

import com.ftn.event_hopper.dtos.solutions.SimpleProductDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CategorySuggestionDTO {
    private UUID id;
    private String name;
    private SimpleProductDTO product;
}