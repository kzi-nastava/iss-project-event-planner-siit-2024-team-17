package com.ftn.event_hopper.dtos.budgets;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UpdateBudgetItemDTO {
    private UUID id;
    private UUID categoryId;
    private double amount;
}
