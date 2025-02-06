package com.ftn.event_hopper.repositories.blocking;

import com.ftn.event_hopper.models.blocks.Block;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BlockingRepository extends JpaRepository<Block, UUID> {
}
