package com.ftn.event_hopper.services;

import com.ftn.event_hopper.dtos.users.account.*;
import com.ftn.event_hopper.mapper.AccountDTOMapper;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountDTOMapper accountDTOMapper;

    public AccountDTO findOneAccount(UUID id) {
        return accountDTOMapper.fromAccountToAccountDTO(accountRepository.findById(id).orElseGet(null));
    }

    public SimpleAccountDTO findOneSimpleAccount(UUID id) {
        return accountDTOMapper.fromAccountToSimpleDTO(accountRepository.findById(id).orElseGet(null));
    }

    public List<SimpleAccountDTO> findAllActive() {
        List<Account> accounts = accountRepository.findByActive(true);
        return accountDTOMapper.fromAccountListToSimpleDTOList(accounts);
    }

    public List<SimpleAccountDTO> findAllVerified(){
        List<Account> accounts = accountRepository.findByVerified(true);
        return accountDTOMapper.fromAccountListToSimpleDTOList(accounts);
    }

    public List<SimpleAccountDTO> findAllInactive() {
        List<Account> accounts = accountRepository.findByActive(false);
        return accountDTOMapper.fromAccountListToSimpleDTOList(accounts);
    }

    public SimpleAccountDTO findByEmailAndPassword(LoginDTO loginDTO) {
        return accountDTOMapper.fromAccountToSimpleDTO(accountRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword()));
    }

    public List<AccountDTO> findAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accountDTOMapper.fromAccountListToAccountDTOList(accounts);
    }

    public List<SimpleAccountDTO> findAllSimpleAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accountDTOMapper.fromAccountListToSimpleDTOList(accounts);
    }


    public CreatedAccountDTO create(CreateAccountDTO accountDTO){
        Account account = accountDTOMapper.fromCreateDTOToAccount(accountDTO);
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
