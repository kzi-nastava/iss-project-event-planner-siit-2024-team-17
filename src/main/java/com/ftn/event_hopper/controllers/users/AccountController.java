package com.ftn.event_hopper.controllers.users;

import com.ftn.event_hopper.dtos.users.account.*;
import com.ftn.event_hopper.dtos.users.person.ProfileForPersonDTO;
import com.ftn.event_hopper.dtos.users.person.UpdatePersonDTO;
import com.ftn.event_hopper.dtos.users.serviceProvider.CompanyDetailsDTO;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.models.verification.VerificationTokenState;
import com.ftn.event_hopper.services.users.AccountService;
import com.ftn.event_hopper.services.verification.VerificationService;
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
    @Autowired
    private VerificationService verificationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccountDTO>> getAccounts() {
        List<AccountDTO> accounts = accountService.findAllAccounts();
        if(accounts == null) {
            return new ResponseEntity<Collection<AccountDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PostMapping(value = "/check-email")
    public ResponseEntity<Boolean> isEmailTaken(@RequestBody String email) {
        return accountService.findByEmail(email).isPresent() ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(false, HttpStatus.OK);
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


    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProfileForPersonDTO> getProfile() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(account == null) {
            return new ResponseEntity<ProfileForPersonDTO>(HttpStatus.NOT_FOUND);
        }
        ProfileForPersonDTO profileForPerson = accountService.getProfile(account.getId());
        if (profileForPerson == null) {
            return new ResponseEntity<ProfileForPersonDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(profileForPerson, HttpStatus.OK);
    }

    @PostMapping(value = "/change-profile-picture", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeProfilePicture(@RequestBody String newProfilePicture) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accountService.changeProfilePicture(account.getId(), newProfilePicture);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


    @PostMapping(value = "/deactivate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deactivate() {
        try {
            Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            accountService.deactivate(account.getId());
            return ResponseEntity.status(HttpStatus.ACCEPTED).build(); // Success
        } catch (RuntimeException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }


    @GetMapping(value = "/verify/{token}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VerificationTokenState> verify(@PathVariable String token) {
        VerificationTokenState state = verificationService.verifyToken(token);
        if(state == VerificationTokenState.EXPIRED) {
            accountService.deleteByEmail(verificationService.getEmailByToken(token));
        }

        if(state == VerificationTokenState.ACCEPTED) {
            accountService.verify(verificationService.getEmailByToken(token));
        }
        return new ResponseEntity<>(state, HttpStatus.OK);
    }

    @GetMapping(value = "/resend-verification-email/{token}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> resendVerificationEmail(@PathVariable String token) {
        String email = verificationService.getEmailByToken(token);
        Boolean success = verificationService.sendVerificationEmail(email);
        if(success){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
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

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedAccountDTO> updateAccount(@RequestBody UpdatePersonDTO personDTO) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(account == null) {
            return new ResponseEntity<UpdatedAccountDTO>(HttpStatus.NO_CONTENT);
        }
        UpdatedAccountDTO updatedAccount = accountService.update(account.getId(), personDTO);
        if(updatedAccount == null) {
            return new ResponseEntity<UpdatedAccountDTO>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @PutMapping(value = "/company", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedCompanyAccountDTO> updateCompanyAccount(@RequestBody UpdateCompanyAccountDTO companyAccountDTO) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(account == null) {
            return new ResponseEntity<UpdatedCompanyAccountDTO>(HttpStatus.NOT_FOUND);
        }
        UpdatedCompanyAccountDTO updatedAccount = accountService.updateCompanyAccount(account.getId(), companyAccountDTO);
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

}