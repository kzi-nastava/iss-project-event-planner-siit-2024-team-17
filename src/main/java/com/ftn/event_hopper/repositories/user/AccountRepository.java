package com.ftn.event_hopper.repositories.user;

import com.ftn.event_hopper.models.users.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID>{
    List<Account> findByIsVerified(boolean verified);
    List<Account> findByIsActive(boolean active);

    Account findByEmailAndPassword(String email, String password);
}
