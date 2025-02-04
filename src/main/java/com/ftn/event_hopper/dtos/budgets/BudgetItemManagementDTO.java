package com.ftn.event_hopper.dtos.budgets;

import com.ftn.event_hopper.dtos.categories.SimpleCategoryDTO;
import com.ftn.event_hopper.dtos.solutions.SimpleProductDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class BudgetItemManagementDTO {
    private UUID id;
    private SimpleCategoryDTO category;
    private double amount;
    private double minAmount;
    private boolean isDeletable;
    private Collection<SimpleProductDTO> purchasedProducts;
}
