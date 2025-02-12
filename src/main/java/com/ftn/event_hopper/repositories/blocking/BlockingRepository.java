package com.ftn.event_hopper.repositories.blocking;

import com.ftn.event_hopper.models.blocks.Block;
import com.ftn.event_hopper.models.users.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BlockingRepository extends JpaRepository<Block, UUID> {
    Optional<Block> findByWhoAndBlocked(Account who, Account blocked);
}
