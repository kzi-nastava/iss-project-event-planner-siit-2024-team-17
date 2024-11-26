package com.ftn.event_hopper.dtos.solutions;

import com.ftn.event_hopper.models.categories.Category;
import com.ftn.event_hopper.models.comments.Comment;
import com.ftn.event_hopper.models.eventTypes.EventType;
import com.ftn.event_hopper.models.prices.Price;
import com.ftn.event_hopper.models.ratings.Rating;
import com.ftn.event_hopper.models.shared.ProductStatus;
import com.ftn.event_hopper.models.users.EventOrganizer;
import com.ftn.event_hopper.models.users.ServiceProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class GetServiceDTO {
    private UUID id;
    private String name;
    private String description;
    private Collection<String> pictures;
    private boolean isAvailable;
    private boolean isVisible;
    private ProductStatus status;
    private Collection<UUID> ratingsIds;
    private Collection<UUID> commentsIds;
    private UUID priceId;
    private UUID serviceProviderId;
    private UUID categoryId;
    private Collection<UUID> eventTypesIds;
    private int durationMinutes;
    private int reservationWindowDays;
    private int cancellationWindowDays;
    private boolean isAutoAccept;
}
