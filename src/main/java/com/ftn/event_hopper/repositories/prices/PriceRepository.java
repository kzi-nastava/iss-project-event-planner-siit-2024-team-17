package com.ftn.event_hopper.repositories.prices;

import com.ftn.event_hopper.models.prices.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PriceRepository extends JpaRepository<Price, UUID> {
}
