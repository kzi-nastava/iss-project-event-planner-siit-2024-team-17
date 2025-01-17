package com.ftn.event_hopper.dtos.solutions;

import com.ftn.event_hopper.dtos.categories.SimpleCategoryDTO;
import com.ftn.event_hopper.dtos.comments.SimpleCommentDTO;
import com.ftn.event_hopper.dtos.eventTypes.SimpleEventTypeDTO;
import com.ftn.event_hopper.dtos.prices.SimplePriceDTO;
import com.ftn.event_hopper.dtos.users.serviceProvider.SimpleServiceProviderDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class SolutionDetailsDTO {
    private UUID id;
    private String name;
    private String description;
    private boolean isAvailable;
    private SimplePriceDTO price;
    private SimpleCategoryDTO category;
    private Collection<SimpleEventTypeDTO> eventTypes;
    private Collection<String> pictures;
    private double rating;
    private Collection<SimpleCommentDTO> comments;
    private SimpleServiceProviderDTO provider;
    private boolean isService;
    private int durationMinutes;
    private int reservationWindowDays;
    private int cancellationWindowDays;
    private boolean isFavorite;
    private boolean isPendingComment;
    private boolean isPendingRating;
}
