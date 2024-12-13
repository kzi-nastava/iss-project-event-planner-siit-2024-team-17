package com.ftn.event_hopper.services.users;

import com.ftn.event_hopper.dtos.registration.CreatedRegistrationRequestDTO;
import com.ftn.event_hopper.dtos.users.account.*;
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

    public AccountDTO findOneAccount(UUID id) {
        return accountDTOMapper.fromAccountToAccountDTO(accountRepository.findById(id).orElseGet(null));
    }

    public SimpleAccountDTO findOneSimpleAccount(UUID id) {
        return accountDTOMapper.fromAccountToSimpleDTO(accountRepository.findById(id).orElseGet(null));
    }

    public List<SimpleAccountDTO> findAllActive() {
        List<Account> accounts = accountRepository.findByIsActive(true);
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

    public Optional<SimpleAccountDTO> findByEmailAndPassword(LoginDTO loginDTO) {
        Optional<Account> accountOptional = accountRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        return accountOptional.map(accountDTOMapper::fromAccountToSimpleDTO);
    }


    public List<AccountDTO> findAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accountDTOMapper.fromAccountListToAccountDTOList(accounts);
    }

    public List<SimpleAccountDTO> findAllSimpleAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accountDTOMapper.fromAccountListToSimpleDTOList(accounts);
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

    public UpdatedAccountDTO update(UUID id, UpdateAccountDTO accountDTO){
        Account account = accountRepository.findById(id).orElseGet(null);
        if(account!= null){
            account.setPassword(accountDTO.getPassword());
            account.setVerified(accountDTO.isVerified());
            account.setActive(accountDTO.isActive());
            account.setSuspensionTimestamp(accountDTO.getSuspensionTimeStamp());
            account.setType(accountDTO.getType());

            this.save(account);
        }
        return accountDTOMapper.fromAccountToUpdatedDTO(account);
    }

    public SimpleAccountDTO deactivate(UUID accountId){
        Account account = accountRepository.findById(accountId).orElseGet(null);
        if(account!= null){
            account.setActive(false);
            this.save(account);
        }
        return accountDTOMapper.fromAccountToSimpleDTO(account);
    }

    public SimpleAccountDTO verify(UUID accountId){
        Account account = accountRepository.findById(accountId).orElseGet(null);
        if(account!= null){
            account.setVerified(true);
            this.save(account);
        }
        return accountDTOMapper.fromAccountToSimpleDTO(account);
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
