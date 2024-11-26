package com.ftn.event_hopper.dtos.solutions;

import com.ftn.event_hopper.models.shared.ProductStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class GetProductDTO {
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
}
