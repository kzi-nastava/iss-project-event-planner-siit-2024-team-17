package com.ftn.event_hopper.dtos.budgets;

import com.ftn.event_hopper.dtos.events.SimpleEventDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class BudgetManagementDTO {
    private double leftAmount;
    private Collection<BudgetItemManagementDTO> budgetItems;
    private SimpleEventDTO event;
}
