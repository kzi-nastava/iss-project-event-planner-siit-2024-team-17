package com.ftn.event_hopper.controllers.users;

import com.ftn.event_hopper.dtos.users.account.*;
import com.ftn.event_hopper.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccountDTO>> getAccounts() {
        return new ResponseEntity<>(accountService.findAllAccounts(), HttpStatus.OK);
    }

    @GetMapping(value = "/simple",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimpleAccountDTO>> getSimpleAccounts() {
        return new ResponseEntity<>(accountService.findAllSimpleAccounts(), HttpStatus.OK);
    }

    @GetMapping(value = "/verified", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimpleAccountDTO>> getVerifiedAccounts() {
        return new ResponseEntity<>(accountService.getAllVerified(), HttpStatus.OK);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimpleAccountDTO> getLoginAccount(@RequestBody LoginDTO loginDTO) {
        return new ResponseEntity<>(accountService.findByEmailAndPassword(loginDTO), HttpStatus.OK);
    }

    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimpleAccountDTO>> getActiveAccounts() {
        return new ResponseEntity<>(accountService.findAllActive(), HttpStatus.OK);
    }

    @GetMapping(value = "/inactive", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimpleAccountDTO>> getInactiveAccounts() {
        return new ResponseEntity<>(accountService.findAllInactive(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> getAccount(@RequestBody UUID id) {
        return new ResponseEntity<>(accountService.findOneAccount(id) , HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}/simple", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimpleAccountDTO> getSimpleAccount(@RequestBody UUID id) {
        return new ResponseEntity<>(accountService.findOneSimpleAccount(id) , HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedAccountDTO> createAccount(@RequestBody CreateAccountDTO accountDTO) {
        return new ResponseEntity<>(accountService.create(accountDTO), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedAccountDTO> updateAccount(@PathVariable UUID id, @RequestBody UpdateAccountDTO accountDTO) {
        return new ResponseEntity<>(accountService.update(id, accountDTO), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteAccount(@PathVariable UUID id) {
        boolean success = accountService.delete(id);
        if(success) {
            return new ResponseEntity<>("Account with ID " + id + " deleted successfully.", HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>("Account with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }
}