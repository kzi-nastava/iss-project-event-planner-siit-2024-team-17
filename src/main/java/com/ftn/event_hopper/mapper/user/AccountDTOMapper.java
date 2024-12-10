package com.ftn.event_hopper.mapper.user;

import com.ftn.event_hopper.dtos.users.person.CreatePersonDTO;
import com.ftn.event_hopper.dtos.users.person.SimplePersonDTO;
import com.ftn.event_hopper.dtos.users.account.SimpleAccountDTO;
import com.ftn.event_hopper.dtos.users.account.*;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.models.users.Person;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountDTOMapper {
    private final ModelMapper modelMapper;
    private final PersonDTOMapper personDTOMapper;

    @Autowired
    public AccountDTOMapper(ModelMapper modelMapper, PersonDTOMapper personDTOMapper) {
        this.modelMapper = modelMapper;
        this.personDTOMapper = personDTOMapper;
        configureMappings();
    }

    private void configureMappings(){
        Converter<Person, SimplePersonDTO> personConverter = context ->
                personDTOMapper.fromPersonToSimpleDTO(context.getSource());

        Converter<Person, CreatePersonDTO> createPersonConverter = context ->
                personDTOMapper.fromPersonToCreateDTO(context.getSource());

        // Custom mapping for Account -> SimpleAccountDTO
        modelMapper.typeMap(Account.class, SimpleAccountDTO.class)
                .addMappings(mapper -> mapper.using(personConverter)
                        .map(Account::getPerson, SimpleAccountDTO::setPerson));

        // Custom mapping for Account -> AccountDTO
        modelMapper.typeMap(Account.class, AccountDTO.class)
                .addMappings(mapper -> mapper.using(personConverter)
                        .map(Account::getPerson, AccountDTO::setPerson));

        // Custom mapping for Account -> CreateAccountDTO
        modelMapper.typeMap(Account.class, CreateAccountDTO.class)
                .addMappings(mapper -> mapper.using(createPersonConverter)
                        .map(Account::getPerson, CreateAccountDTO::setPerson));

        // Custom mapping for Account -> CreatedAccountDTO
        modelMapper.typeMap(Account.class, CreatedAccountDTO.class)
                .addMappings(mapper -> mapper.using(personConverter)
                        .map(Account::getPerson, CreatedAccountDTO::setPerson));

        // Custom mapping for Account -> UpdatedAccountDTO
        modelMapper.typeMap(Account.class, UpdatedAccountDTO.class)
                .addMappings(mapper -> mapper.using(personConverter)
                        .map(Account::getPerson, UpdatedAccountDTO::setPerson));




    }

    public SimpleAccountDTO fromAccountToSimpleDTO(Account account){
        return modelMapper.map(account, SimpleAccountDTO.class);
    }

    public AccountDTO fromAccountToAccountDTO(Account account){
        return modelMapper.map(account, AccountDTO.class);
    }

    public CreateAccountDTO fromAccountToCreateDTO(Account account){
        return modelMapper.map(account, CreateAccountDTO.class);
    }

    public CreatedAccountDTO fromAccountToCreatedDTO(Account account){
        return modelMapper.map(account, CreatedAccountDTO.class);
    }

    public UpdatedAccountDTO fromAccountToUpdatedDTO(Account account){
        return modelMapper.map(account, UpdatedAccountDTO.class);
    }

    public Account fromCreateDTOToAccount(CreateAccountDTO createAccountDTO){
        Account account = modelMapper.map(createAccountDTO, Account.class);
        account.setPerson(personDTOMapper.fromCreatePersonDTOToPerson(createAccountDTO.getPerson()));
        return account;
    }

    public Account fromUpdateDTOToAccount(UpdateAccountDTO updateAccountDTO){
        return modelMapper.map(updateAccountDTO, Account.class);
    }

    public Account fromAccountDTOToAccount(AccountDTO accountDTO){
        Account account = modelMapper.map(accountDTO, Account.class);
        account.setPerson(personDTOMapper.fromSimplePersonDTOToPerson(accountDTO.getPerson()));
        return account;
    }

    public Account fromSimpleAccountDTOToAccount(SimpleAccountDTO accountDTO){
        Account account = modelMapper.map(accountDTO, Account.class);
        account.setPerson(personDTOMapper.fromSimplePersonDTOToPerson(accountDTO.getPerson()));
        return account;
    }


    public List<AccountDTO> fromAccountListToAccountDTOList(List<Account> accounts) {
        return accounts.stream()
                .map(this::fromAccountToAccountDTO)
                .collect(Collectors.toList());
    }

    public List<SimpleAccountDTO> fromAccountListToSimpleDTOList(List<Account> accounts) {
        return accounts.stream()
                .map(this::fromAccountToSimpleDTO)
                .collect(Collectors.toList());
    }

}
