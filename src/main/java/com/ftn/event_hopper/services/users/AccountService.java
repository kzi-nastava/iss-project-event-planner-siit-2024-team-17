package com.ftn.event_hopper.services.users;

import com.ftn.event_hopper.dtos.registration.CreatedRegistrationRequestDTO;
import com.ftn.event_hopper.dtos.users.account.*;
import com.ftn.event_hopper.dtos.users.person.ProfileForPersonDTO;
import com.ftn.event_hopper.dtos.users.person.UpdatePersonDTO;
import com.ftn.event_hopper.mapper.registrationRequests.RegistrationRequestDTOMapper;
import com.ftn.event_hopper.mapper.users.AccountDTOMapper;
import com.ftn.event_hopper.models.registration.RegistrationRequest;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.repositories.users.AccountRepository;
import com.ftn.event_hopper.services.registrationRequests.RegistrationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountDTOMapper accountDTOMapper;
    @Autowired
    private RegistrationRequestService registrationRequestService;
    @Autowired
    private RegistrationRequestDTOMapper registrationRequestDTOMapper;

    @Autowired
    private PersonService personService;

    public AccountDTO findOneAccount(UUID id) {
        return accountDTOMapper.fromAccountToAccountDTO(accountRepository.findById(id).orElseGet(null));
    }

    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public SimpleAccountDTO findOneSimpleAccount(UUID id) {
        return accountDTOMapper.fromAccountToSimpleDTO(accountRepository.findById(id).orElseGet(null));
    }

    public List<SimpleAccountDTO> findAllActive() {
        List<Account> accounts = accountRepository.findByIsActive(true);
        return accountDTOMapper.fromAccountListToSimpleDTOList(accounts);
    }

    public List<SimpleAccountDTO> findAllValid(){
        List<Account> accounts = accountRepository.findByIsVerifiedAndIsActive(true, true);
        return accountDTOMapper.fromAccountListToSimpleDTOList(accounts);
    }

    public List<SimpleAccountDTO> findAllVerified(){
        List<Account> accounts = accountRepository.findByIsVerified(true);
        return accountDTOMapper.fromAccountListToSimpleDTOList(accounts);
    }

    public List<SimpleAccountDTO> findAllInactive() {
        List<Account> accounts = accountRepository.findByIsActive(false);
        return accountDTOMapper.fromAccountListToSimpleDTOList(accounts);
    }

    public List<AccountDTO> findAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accountDTOMapper.fromAccountListToAccountDTOList(accounts);
    }

    public List<SimpleAccountDTO> findAllSimpleAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accountDTOMapper.fromAccountListToSimpleDTOList(accounts);
    }

    public ProfileForPersonDTO getProfile(UUID id){
        Account account = accountRepository.findById(id).orElseGet(null);
        ProfileForPersonDTO profileForPerson = personService.getProfile(account.getPerson().getId());
        profileForPerson.setEmail(account.getEmail());
        return profileForPerson;
    }


    public CreatedServiceProviderAccountDTO createServiceProvider(CreateServiceProviderAccountDTO accountDTO){
        Account account = accountDTOMapper.fromCreateServiceProviderDTOToAccount(accountDTO);
        CreatedRegistrationRequestDTO requestDTO = registrationRequestService.create(accountDTO.getRegistrationRequest());
        RegistrationRequest request = registrationRequestDTOMapper.fromCreatedDTOToRegistrationRequest(requestDTO);
        account.setRegistrationRequest(request);
        this.save(account);
        return accountDTOMapper.fromAccountToCreatedServiceProviderDTO(account);
    }


    public CreatedEventOrganizerAccountDTO createEventOrganizer(CreateEventOrganizerAccountDTO accountDTO){
        Account account = accountDTOMapper.fromCreateOrganizerDTOToAccount(accountDTO);
        CreatedRegistrationRequestDTO requestDTO = registrationRequestService.create(accountDTO.getRegistrationRequest());
        RegistrationRequest request = registrationRequestDTOMapper.fromCreatedDTOToRegistrationRequest(requestDTO);
        account.setRegistrationRequest(request);
        System.out.println(account);
        this.save(account);
        return accountDTOMapper.fromAccountToCreatedEventOrganizerDTO(account);
    }

    public CreatedAccountDTO createPerson(CreatePersonAccountDTO accountDTO){
        Account account = accountDTOMapper.fromCreatePersonDTOToAccount(accountDTO);
        CreatedRegistrationRequestDTO requestDTO = registrationRequestService.create(accountDTO.getRegistrationRequest());
        RegistrationRequest request = registrationRequestDTOMapper.fromCreatedDTOToRegistrationRequest(requestDTO);
        account.setRegistrationRequest(request);
        this.save(account);
        return accountDTOMapper.fromAccountToCreatedDTO(account);
    }

    public UpdatedAccountDTO update(UUID id, UpdatePersonDTO personDTO){
        Account account = accountRepository.findById(id).orElseGet(null);
        if(account!= null){
            personService.update(account.getPerson().getId(), personDTO);
            this.save(account);
        }
        return accountDTOMapper.fromAccountToUpdatedDTO(account);
    }

    public void changePassword(UUID id, ChangePasswordDTO changePasswordDTO){
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            throw new RuntimeException("Account not found.");
        }
        if (!account.getPassword().equals(changePasswordDTO.getOldPassword())) {
            throw new RuntimeException("The old password is incorrect.");
        }
        account.setPassword(changePasswordDTO.getNewPassword());
        this.save(account);
    }

    public void deactivate(UUID accountId){
        Account account = accountRepository.findById(accountId).orElseGet(null);
        if(account!= null){
            account.setActive(false);
            this.save(account);
            return;
        }
        throw new RuntimeException("Account not found.");
    }

    public Optional<SimpleAccountDTO> verify(UUID accountId){
        Account account = accountRepository.findById(accountId).orElseGet(null);
        if(account!= null){
            account.setVerified(true);
            this.save(account);
        }
        return Optional.ofNullable(accountDTOMapper.fromAccountToSimpleDTO(account));
    }

    public SimpleAccountDTO suspend(UUID accountId) {
        Account account = accountRepository.findById(accountId).orElseGet(null);
        if (account != null) {
            account.setActive(false);
            account.setSuspensionTimestamp(LocalDateTime.now());
            this.save(account);
        }
        return accountDTOMapper.fromAccountToSimpleDTO(account);
    }


    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public boolean delete(UUID id) {
        Account account = accountRepository.findById(id).orElseGet(null);
        if(account != null){
            account.setActive(false);
            this.save(account);
            return true;
        }
        return false;
    }

}
