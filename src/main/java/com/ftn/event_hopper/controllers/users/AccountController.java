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
        account1.setPersonId(UUID.randomUUID());
        account1.setRegistrationRequestId(null);

        GetAccountDTO account2 = new GetAccountDTO();
        account2.setId(UUID.randomUUID());
        account2.setEmail("mapa@gmail.com");
        account2.setPassword("Marija123");
        account2.setVerified(true);
        account2.setActive(true);
        account2.setType(PersonType.EVENT_ORGANIZER);
        account2.setSuspensionTimeStamp(LocalDateTime.now());
        account2.setPersonId(UUID.randomUUID());
        account2.setRegistrationRequestId(null);


        accounts.add(account1);
        accounts.add(account2);

        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }



    @GetMapping(value = "/verified", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetAccountDTO> getVerifiedAccounts() {
        // Temporarily faking the data, should be a call to a getVerifiedAccounts method
        GetAccountDTO account = new GetAccountDTO();
        account.setId(UUID.randomUUID());
        account.setEmail("mapa@gmail.com");
        account.setPassword("Marija123");
        account.setVerified(true);
        account.setActive(true);
        account.setType(PersonType.EVENT_ORGANIZER);
        account.setSuspensionTimeStamp(LocalDateTime.now());
        account.setPersonId(UUID.randomUUID());
        account.setRegistrationRequestId(null);

        return new ResponseEntity<>(account, HttpStatus.OK);
    }


    @GetMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetAccountDTO> getLoginAccount() {
        // Temporarily faking the data, should be a call to a getVerifiedAccounts method
        GetAccountDTO account = new GetAccountDTO();
        account.setId(UUID.randomUUID());
        account.setEmail("mapa@gmail.com");
        account.setPassword("Marija123");
        account.setVerified(true);
        account.setActive(true);
        account.setType(PersonType.EVENT_ORGANIZER);
        account.setSuspensionTimeStamp(LocalDateTime.now());
        account.setPersonId(UUID.randomUUID());
        account.setRegistrationRequestId(null);

        return new ResponseEntity<>(account, HttpStatus.OK);
    }



    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetAccountDTO> getActiveAccounts() {
        // Temporarily faking the data, should be a call to a getActiveAccounts method
        GetAccountDTO account = new GetAccountDTO();
        account.setId(UUID.randomUUID());
        account.setEmail("mapa@gmail.com");
        account.setPassword("Marija123");
        account.setVerified(true);
        account.setActive(true);
        account.setType(PersonType.EVENT_ORGANIZER);
        account.setSuspensionTimeStamp(LocalDateTime.now());
        account.setPersonId(UUID.randomUUID());
        account.setRegistrationRequestId(null);

        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetAccountDTO> getAccount(@RequestBody LoginDTO loginDTO ) {
        // Temporarily faking the data
        GetAccountDTO account = new GetAccountDTO();

        if (account == null) {
            return new ResponseEntity<GetAccountDTO>(HttpStatus.NOT_FOUND);
        }


        account.setId(UUID.randomUUID());
        account.setEmail(loginDTO.getEmail());
        account.setPassword(loginDTO.getPassword());
        account.setVerified(true);
        account.setActive(true);
        account.setType(PersonType.EVENT_ORGANIZER);
        account.setSuspensionTimeStamp(LocalDateTime.now());
        account.setPersonId(UUID.randomUUID());
        account.setRegistrationRequestId(null);

        if(account.getEmail().equals(loginDTO.getEmail()) && account.getPassword().equals(loginDTO.getPassword())){
            return new ResponseEntity<>(account, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedAccountDTO> createAccount(@RequestBody CreateAccountDTO account) {
        CreatedAccountDTO createdAccount = new CreatedAccountDTO();
        createdAccount.setId(UUID.randomUUID());
        createdAccount.setEmail(account.getEmail());
        createdAccount.setPassword(account.getPassword());
        createdAccount.setVerified(account.isVerified());
        createdAccount.setActive(account.isActive());
        createdAccount.setType(account.getType());
        createdAccount.setSuspensionTimeStamp(account.getSuspensionTimeStamp());
        createdAccount.setPersonId(account.getPersonId());
        createdAccount.setRegistrationRequestId(account.getRegistrationRequestId());

        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedAccountDTO> updateAccount(@PathVariable UUID id, @RequestBody UpdateAccountDTO account) {
        // Temporarily faking the update process
        UpdatedAccountDTO updatedAccount = new UpdatedAccountDTO();

        updatedAccount.setId(UUID.randomUUID());
        updatedAccount.setPassword(account.getPassword());
        updatedAccount.setVerified(account.isVerified());
        updatedAccount.setActive(account.isActive());
        updatedAccount.setType(account.getType());
        updatedAccount.setSuspensionTimeStamp(account.getSuspensionTimeStamp());
        updatedAccount.setPersonId(account.getPersonId());
        updatedAccount.setRegistrationRequestId(account.getRegistrationRequestId());

        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteAccount(@PathVariable UUID id) {
        // Temporarily faking the data, should be a call to a get by id method
        GetAccountDTO account = new GetAccountDTO();
        account.setId(UUID.randomUUID());
        account.setEmail("mapa@gmail.com");
        account.setPassword("Marija123");
        account.setVerified(true);
        account.setActive(true);
        account.setType(PersonType.EVENT_ORGANIZER);
        account.setSuspensionTimeStamp(LocalDateTime.now());
        account.setPersonId(UUID.randomUUID());
        account.setRegistrationRequestId(null);


        account.setActive(false);
        return new ResponseEntity<>("Account with ID " + id + " deleted successfully.", HttpStatus.NO_CONTENT);
    }
}