package com.ftn.event_hopper.controllers.users;

import com.ftn.event_hopper.dtos.users.account.*;
import com.ftn.event_hopper.models.users.PersonType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetAccountDTO>> getAccounts() {
        // Temporarily faking the data
        Collection<GetAccountDTO> accounts = new ArrayList<>();

        GetAccountDTO account1 = new GetAccountDTO();
        account1.setId(UUID.randomUUID());
        account1.setEmail("mapa@gmail.com");
        account1.setPassword("Marija123");
        account1.setVerified(true);
        account1.setActive(true);
        account1.setType(PersonType.EVENT_ORGANIZER);
        account1.setSuspensionTimeStamp(LocalDateTime.now());
        account1.setPersonUUID(UUID.randomUUID());
        account1.setRegistrationRequest(null);

        GetAccountDTO account2 = new GetAccountDTO();
        account2.setId(UUID.randomUUID());
        account2.setEmail("mapa@gmail.com");
        account2.setPassword("Marija123");
        account2.setVerified(true);
        account2.setActive(true);
        account2.setType(PersonType.EVENT_ORGANIZER);
        account2.setSuspensionTimeStamp(LocalDateTime.now());
        account2.setPersonUUID(UUID.randomUUID());
        account2.setRegistrationRequest(null);


        accounts.add(account1);
        accounts.add(account2);

        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetAccountDTO> getAccount(@PathVariable UUID id) {
        // Temporarily faking the data
        GetAccountDTO account = new GetAccountDTO();
        account.setId(UUID.randomUUID());
        account.setEmail("mapa@gmail.com");
        account.setPassword("Marija123");
        account.setVerified(true);
        account.setActive(true);
        account.setType(PersonType.EVENT_ORGANIZER);
        account.setSuspensionTimeStamp(LocalDateTime.now());
        account.setPersonUUID(UUID.randomUUID());
        account.setRegistrationRequest(null);

        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedAccountDTO> createAccount(@RequestBody CreateAccountDTO account) {
        // Creating a new event account with the provided data
        CreatedAccountDTO createdAccount = new CreatedAccountDTO();
        createdAccount.setId(UUID.randomUUID());
        createdAccount.setEmail("mapa@gmail.com");
        createdAccount.setPassword("Marija123");
        createdAccount.setVerified(true);
        createdAccount.setActive(true);
        createdAccount.setType(PersonType.EVENT_ORGANIZER);
        createdAccount.setSuspensionTimeStamp(LocalDateTime.now());
        createdAccount.setPersonUUID(UUID.randomUUID());
        createdAccount.setRegistrationRequest(null);

        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedAccountDTO> updateAccount(@PathVariable UUID id, @RequestBody UpdatedAccountDTO account) {
        // Temporarily faking the update process
        UpdatedAccountDTO updatedAccount = new UpdatedAccountDTO();
        updatedAccount.setId(UUID.randomUUID());
        updatedAccount.setEmail("mapa@gmail.com");
        updatedAccount.setPassword("Marija123");
        updatedAccount.setVerified(true);
        updatedAccount.setActive(true);
        updatedAccount.setType(PersonType.EVENT_ORGANIZER);
        updatedAccount.setSuspensionTimeStamp(LocalDateTime.now());
        updatedAccount.setPersonUUID(UUID.randomUUID());
        updatedAccount.setRegistrationRequest(null);

        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteAccount(@PathVariable UUID id) {
        // Simulating deletion
        return new ResponseEntity<>("Account with ID " + id + " deleted successfully.", HttpStatus.OK);
    }
}