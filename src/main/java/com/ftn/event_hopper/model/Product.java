package com.ftn.event_hopper.model;

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
    private Collection<Rating> ratings;
    private Collection<Comment> comments;
    private Collection<Price> prices;
    private ServiceProvider serviceProvider;
    private Category category;
    private Collection<EventType> eventTypes;
    private Collection<EventOrganizer> eventOrganizers;
}
