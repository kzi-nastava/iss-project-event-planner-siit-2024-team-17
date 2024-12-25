package com.ftn.event_hopper.controllers.users;

import com.ftn.event_hopper.dtos.users.account.*;
import com.ftn.event_hopper.dtos.users.person.ProfileForPersonDTO;
import com.ftn.event_hopper.dtos.users.person.UpdatePersonDTO;
import com.ftn.event_hopper.dtos.users.serviceProvider.CompanyDetailsDTO;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.services.users.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/accounts")
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


    @GetMapping(value = "/valid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimpleAccountDTO>> getValidAccounts() {
        List<SimpleAccountDTO> accounts = accountService.findAllValid();
        if(accounts == null) {
            return new ResponseEntity<Collection<SimpleAccountDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }


    @GetMapping(value = "/verified", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimpleAccountDTO>> getVerifiedAccounts() {
        List<SimpleAccountDTO> accounts = accountService.findAllVerified();
        if(accounts == null) {
            return new ResponseEntity<Collection<SimpleAccountDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }


    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimpleAccountDTO>> getActiveAccounts() {
        List<SimpleAccountDTO> accounts = accountService.findAllActive();
        if(accounts == null) {
            return new ResponseEntity<Collection<SimpleAccountDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping(value = "/active/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimpleAccountDTO> getActiveByEmail(@PathVariable String email) {
        SimpleAccountDTO account = accountService.findActiveByEmail(email);
        if(account == null) {
            return new ResponseEntity<SimpleAccountDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping(value = "/inactive", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimpleAccountDTO>> getInactiveAccounts() {
        List<SimpleAccountDTO> accounts = accountService.findAllInactive();
        if(accounts == null) {
            return new ResponseEntity<Collection<SimpleAccountDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }


    @GetMapping(value = "/{id}/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProfileForPersonDTO> getProfile(@PathVariable UUID id) {
        ProfileForPersonDTO profileForPerson = accountService.getProfile(id);
        if (profileForPerson == null) {
            return new ResponseEntity<ProfileForPersonDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(profileForPerson, HttpStatus.OK);
    }


    @PostMapping(value = "{id}/deactivate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deactivate(@PathVariable UUID id) {
        try {
            accountService.deactivate(id);
            System.out.println("Account deactivated");
            return ResponseEntity.status(HttpStatus.ACCEPTED).build(); // Success
        } catch (RuntimeException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping(value = "{id}/verify", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> verify(@PathVariable UUID id, @RequestBody ChangePasswordDTO newPasswordDTO) {
        Optional<SimpleAccountDTO> account = accountService.verify(id);
        if(account.isPresent()) {
            return new ResponseEntity<>("Account has been verified", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Account couldn't be verified", HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/change-password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        try {
            Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            accountService.changePassword(account.getId(), changePasswordDTO);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Success
        } catch (RuntimeException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping(value = "/person", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedAccountDTO> createAccount(@RequestBody CreatePersonAccountDTO accountDTO) {
        return new ResponseEntity<>(accountService.createPerson(accountDTO), HttpStatus.CREATED);
    }

    @PostMapping(value = "/event-organizer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedEventOrganizerAccountDTO> createAccount(@RequestBody CreateEventOrganizerAccountDTO accountDTO) {
        return new ResponseEntity<>(accountService.createEventOrganizer(accountDTO), HttpStatus.CREATED);
    }

    @PostMapping(value = "/service-provider", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedServiceProviderAccountDTO> createAccount(@RequestBody CreateServiceProviderAccountDTO accountDTO) {
        return new ResponseEntity<>(accountService.createServiceProvider(accountDTO), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedAccountDTO> updateAccount(@PathVariable UUID id, @RequestBody UpdatePersonDTO personDTO) {
        UpdatedAccountDTO updatedAccount = accountService.update(id, personDTO);
        if(updatedAccount == null) {
            return new ResponseEntity<UpdatedAccountDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/company", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedCompanyAccountDTO> updateCompanyAccount(@PathVariable UUID id, @RequestBody UpdateCompanyAccountDTO companyAccountDTO) {
        UpdatedCompanyAccountDTO updatedAccount = accountService.updateCompanyAccount(id, companyAccountDTO);
        if(updatedAccount == null) {
            return new ResponseEntity<UpdatedCompanyAccountDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @PutMapping(value = "/upgrade-to-OD/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedAccountDTO> upgradeToOD(@PathVariable UUID id) {
        UpdatedAccountDTO updatedAccountDTO = accountService.updateToOD(id);
        if(updatedAccountDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedAccountDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/upgrade-to-PUP/{id}" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedAccountDTO> upgradeToPUP(@PathVariable UUID id, @RequestBody CompanyDetailsDTO companyDetailsDTO) {
        UpdatedAccountDTO updatedAccountDTO = accountService.updateToPUP(id, companyDetailsDTO);
        if(updatedAccountDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedAccountDTO, HttpStatus.OK);
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