package com.ftn.event_hopper.controllers.users;

import com.ftn.event_hopper.dtos.users.account.*;
import com.ftn.event_hopper.services.user.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccountDTO>> getAccounts() {
        List<AccountDTO> accounts = accountService.findAllAccounts();
        if(accounts == null) {
            return new ResponseEntity<Collection<AccountDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping(value = "/simple",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimpleAccountDTO>> getSimpleAccounts() {
        List<SimpleAccountDTO> accounts = accountService.findAllSimpleAccounts();
        if(accounts == null) {
            return new ResponseEntity<Collection<SimpleAccountDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> getAccount(@PathVariable UUID id) {
        AccountDTO account = accountService.findOneAccount(id);
        if (account == null) {
            return new ResponseEntity<AccountDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/simple", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimpleAccountDTO> getSimpleAccount(@PathVariable UUID id) {
        SimpleAccountDTO account = accountService.findOneSimpleAccount(id);
        if (account == null) {
            return new ResponseEntity<SimpleAccountDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(account, HttpStatus.OK);
    }



    @GetMapping(value = "/verified", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimpleAccountDTO>> getVerifiedAccounts() {
        List<SimpleAccountDTO> accounts = accountService.findAllVerified();
        if(accounts == null) {
            return new ResponseEntity<Collection<SimpleAccountDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimpleAccountDTO> getLoginAccount(@RequestBody LoginDTO loginDTO) {
        SimpleAccountDTO account = accountService.findByEmailAndPassword(loginDTO);
        if(account == null) {
            return new ResponseEntity<SimpleAccountDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimpleAccountDTO>> getActiveAccounts() {
        List<SimpleAccountDTO> accounts = accountService.findAllActive();
        if(accounts == null) {
            return new ResponseEntity<Collection<SimpleAccountDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping(value = "/inactive", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimpleAccountDTO>> getInactiveAccounts() {
        List<SimpleAccountDTO> accounts = accountService.findAllInactive();
        if(accounts == null) {
            return new ResponseEntity<Collection<SimpleAccountDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedAccountDTO> createAccount(@RequestBody CreateAccountDTO accountDTO) {
        return new ResponseEntity<>(accountService.create(accountDTO), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedAccountDTO> updateAccount(@PathVariable UUID id, @RequestBody UpdateAccountDTO accountDTO) {
        UpdatedAccountDTO updatedAccount = accountService.update(id, accountDTO);
        if(updatedAccount == null) {
            return new ResponseEntity<UpdatedAccountDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
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