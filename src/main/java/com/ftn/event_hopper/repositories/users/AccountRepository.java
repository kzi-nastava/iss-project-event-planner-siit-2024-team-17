package com.ftn.event_hopper.repositories.users;

import com.ftn.event_hopper.models.users.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID>{
    List<Account> findByIsVerified(boolean verified);
    List<Account> findByIsActive(boolean active);
    List<Account> findByIsVerifiedAndIsActive(boolean verified, boolean active);

    Optional<Account> findByEmailAndPassword(String email, String password);
    Optional<Account> findByEmail(String email);
    Optional<Account> findByIsActiveAndEmail(boolean b, String email);
}
