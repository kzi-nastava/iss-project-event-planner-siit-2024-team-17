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


        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping(value = "/verified", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetAccountDTO> getVerifiedAccounts() {

        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetAccountDTO> getLoginAccount() {


        return new ResponseEntity<>(account, HttpStatus.OK);
    }


    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetAccountDTO> getActiveAccounts() {


        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetAccountDTO> getAccount(@RequestBody LoginDTO loginDTO ) {

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedAccountDTO> createAccount(@RequestBody CreateAccountDTO account) {

        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedAccountDTO> updateAccount(@PathVariable UUID id, @RequestBody UpdateAccountDTO account) {


        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteAccount(@PathVariable UUID id) {

        return new ResponseEntity<>("Account with ID " + id + " deleted successfully.", HttpStatus.NO_CONTENT);
    }
}