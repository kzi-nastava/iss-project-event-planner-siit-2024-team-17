package com.ftn.event_hopper.dtos.categories;

import com.ftn.event_hopper.models.shared.CategoryStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateCategoryDTO {
    private String name;
    private String description;
}
