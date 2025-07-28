package com.ftn.event_hopper.dtos.eventTypes;

import com.ftn.event_hopper.dtos.categories.SimpleCategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventTypeDTO {
    private String description;
    private List<SimpleCategoryDTO> suggestedCategories;
}
