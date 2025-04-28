package com.ftn.event_hopper.repositories.budgets;

import com.ftn.event_hopper.models.budgets.BudgetItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BudgetRepository extends JpaRepository<BudgetItem, UUID> {
}
