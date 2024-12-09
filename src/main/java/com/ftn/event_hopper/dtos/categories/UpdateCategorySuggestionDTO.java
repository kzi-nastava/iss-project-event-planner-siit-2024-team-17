package com.ftn.event_hopper.dtos.categories;

import com.ftn.event_hopper.models.shared.CategoryStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UpdateCategorySuggestionDTO {
    private UUID id;
    private CategoryStatus status;
}
