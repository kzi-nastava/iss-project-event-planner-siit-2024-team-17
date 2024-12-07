package com.ftn.event_hopper.models.solutions;

import com.ftn.event_hopper.models.categories.Category;
import com.ftn.event_hopper.models.comments.Comment;
import com.ftn.event_hopper.models.eventTypes.EventType;
import com.ftn.event_hopper.models.prices.Price;
import com.ftn.event_hopper.models.ratings.Rating;
import com.ftn.event_hopper.models.shared.ProductStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Product {
    private UUID id;
    private String name;
    private String description;
    private Collection<String> pictures;
    private boolean isAvailable;
    private boolean isVisible;
    private ProductStatus status;
    private LocalDateTime editTimestamp;
    private boolean isDeleted;
    private Collection<Price> prices;
    private Collection<Comment> comments;
    private Collection<Rating> ratings;
    private Category category;
    private Collection<EventType> eventTypes;
}
