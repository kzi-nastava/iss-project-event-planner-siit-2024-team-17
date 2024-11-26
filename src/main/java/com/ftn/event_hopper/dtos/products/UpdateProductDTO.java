package com.ftn.event_hopper.dtos.products;


import com.ftn.event_hopper.models.categories.Category;
import com.ftn.event_hopper.models.comments.Comment;
import com.ftn.event_hopper.models.eventTypes.EventType;
import com.ftn.event_hopper.models.ratings.Rating;
import com.ftn.event_hopper.models.shared.ProductStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UpdateProductDTO {
    private String name;
    private String description;
    private Collection<String> pictures;
    private boolean isAvailable;
    private boolean isVisible;
    private ProductStatus status;
    private LocalDateTime editTimestamp;
    private boolean isDeleted;
    private Collection<Rating> ratings;
    private Collection<Comment> comments;
    private Collection<UUID> pricesIds;
    private UUID serviceProviderId;
    private Collection<EventType> eventTypes;
    private Collection<UUID> eventOrganizersIds;
}
