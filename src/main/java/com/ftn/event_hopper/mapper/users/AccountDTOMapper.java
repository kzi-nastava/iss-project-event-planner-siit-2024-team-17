package com.ftn.event_hopper.mapper.users;

import com.ftn.event_hopper.dtos.users.eventOrganizer.SimpleEventOrganizerDTO;
import com.ftn.event_hopper.dtos.users.person.CreatePersonDTO;
import com.ftn.event_hopper.dtos.users.person.SimplePersonDTO;
import com.ftn.event_hopper.dtos.users.account.SimpleAccountDTO;
import com.ftn.event_hopper.dtos.users.account.*;
import com.ftn.event_hopper.dtos.users.serviceProvider.SimpleServiceProviderDTO;
import com.ftn.event_hopper.models.users.*;
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
    private final ServiceProviderDTOMapper serviceProviderDTOMapper;
    private final EventOrganizerDTOMapper eventOrganizerDTOMapper;

    @Autowired
    public AccountDTOMapper(ModelMapper modelMapper, PersonDTOMapper personDTOMapper, ServiceProviderDTOMapper serviceProviderDTOMapper, EventOrganizerDTOMapper eventOrganizerDTOMapper) {
        this.modelMapper = modelMapper;
        this.personDTOMapper = personDTOMapper;
        this.serviceProviderDTOMapper = serviceProviderDTOMapper;
        this.eventOrganizerDTOMapper = eventOrganizerDTOMapper;
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
        modelMapper.typeMap(Account.class, CreateServiceProviderAccountDTO.class)
                .addMappings(mapper -> mapper.using(createPersonConverter)
                        .map(Account::getPerson, CreateServiceProviderAccountDTO::setPerson));

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

    public CreateServiceProviderAccountDTO fromAccountToCreateDTO(Account account){
        return modelMapper.map(account, CreateServiceProviderAccountDTO.class);
    }

    public CreatedAccountDTO fromAccountToCreatedDTO(Account account){
        return modelMapper.map(account, CreatedAccountDTO.class);
    }

    public CreatedEventOrganizerAccountDTO fromAccountToCreatedEventOrganizerDTO(Account account){
        CreatedEventOrganizerAccountDTO dto = modelMapper.map(account, CreatedEventOrganizerAccountDTO.class);

        // Manually map the 'person' field to the correct SimpleServiceProviderDTO
        if (account.getPerson() instanceof EventOrganizer) {
            EventOrganizer eventOrganizer = (EventOrganizer) account.getPerson();
            SimpleEventOrganizerDTO eventOrganizerDTO = eventOrganizerDTOMapper.fromEventOrganizerToSimpleDTO(eventOrganizer);
            dto.setPerson(eventOrganizerDTO);
        }

        return dto;

    }



    public CreatedServiceProviderAccountDTO fromAccountToCreatedServiceProviderDTO(Account account){
        CreatedServiceProviderAccountDTO dto = modelMapper.map(account, CreatedServiceProviderAccountDTO.class);

        // Manually map the 'person' field to the correct SimpleServiceProviderDTO
        if (account.getPerson() instanceof ServiceProvider) {
            ServiceProvider serviceProvider = (ServiceProvider) account.getPerson();
            SimpleServiceProviderDTO serviceProviderDTO = serviceProviderDTOMapper.fromServiceProviderToSimpleDTO(serviceProvider);
            dto.setPerson(serviceProviderDTO);
        }

        return dto;

    }

    public UpdatedAccountDTO fromAccountToUpdatedDTO(Account account){
        return modelMapper.map(account, UpdatedAccountDTO.class);
    }

    public Account fromCreatePersonDTOToAccount(CreatePersonAccountDTO createAccountDTO){
        Account account = new Account();
        Person person = personDTOMapper.fromCreatePersonDTOToPerson(createAccountDTO.getPerson());
        account.setPerson(person);
        account.setEmail(createAccountDTO.getEmail());
        account.setPassword(createAccountDTO.getPassword());
        account.setVerified(createAccountDTO.isVerified());
        account.setActive(true);
        account.setSuspensionTimestamp(createAccountDTO.getSuspensionTimeStamp());
        account.setType(createAccountDTO.getType());

        return account;
    }

    public Account fromCreateServiceProviderDTOToAccount(CreateServiceProviderAccountDTO createAccountDTO){
        Account account = new Account();
        if(createAccountDTO.getType() == PersonType.SERVICE_PROVIDER){
            ServiceProvider provider = serviceProviderDTOMapper.fromCreateServiceProviderDTOToServiceProvider(createAccountDTO.getPerson());
            account.setPerson(provider);
        }
        account.setEmail(createAccountDTO.getEmail());
        account.setActive(true);
        account.setPassword(createAccountDTO.getPassword());
        account.setVerified(createAccountDTO.isVerified());
        account.setSuspensionTimestamp(createAccountDTO.getSuspensionTimeStamp());
        account.setType(createAccountDTO.getType());

        return account;
    }

    public Account fromCreateOrganizerDTOToAccount(CreateEventOrganizerAccountDTO createAccountDTO){
        Account account = new Account();
        if(createAccountDTO.getType() == PersonType.EVENT_ORGANIZER){
            EventOrganizer organizer = eventOrganizerDTOMapper.fromCreateDTOToEventOrganizer(createAccountDTO.getPerson());
            account.setPerson(organizer);
        }
        account.setEmail(createAccountDTO.getEmail());
        account.setPassword(createAccountDTO.getPassword());
        account.setVerified(createAccountDTO.isVerified());
        account.setActive(true);
        account.setSuspensionTimestamp(createAccountDTO.getSuspensionTimeStamp());
        account.setType(createAccountDTO.getType());

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
