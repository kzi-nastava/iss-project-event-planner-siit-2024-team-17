package com.ftn.event_hopper.services.users;

import com.ftn.event_hopper.dtos.events.SimpleEventDTO;
import com.ftn.event_hopper.dtos.location.LocationDTO;
import com.ftn.event_hopper.dtos.location.SimpleLocationDTO;
import com.ftn.event_hopper.dtos.registration.CreatedRegistrationRequestDTO;
import com.ftn.event_hopper.dtos.users.account.*;
import com.ftn.event_hopper.dtos.users.person.ProfileForPersonDTO;
import com.ftn.event_hopper.dtos.users.person.UpdatePersonDTO;
import com.ftn.event_hopper.mapper.events.EventDTOMapper;
import com.ftn.event_hopper.mapper.registrationRequests.RegistrationRequestDTOMapper;
import com.ftn.event_hopper.mapper.users.AccountDTOMapper;
import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.registration.RegistrationRequest;
import com.ftn.event_hopper.models.users.*;
import com.ftn.event_hopper.repositories.users.AccountRepository;
import com.ftn.event_hopper.repositories.users.EventOrganizerRepository;
import com.ftn.event_hopper.repositories.users.ServiceProviderRepository;
import com.ftn.event_hopper.services.registrationRequests.RegistrationRequestService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountDTOMapper accountDTOMapper;
    @Autowired
    private EventDTOMapper eventDTOMapper;
    @Autowired
    private RegistrationRequestService registrationRequestService;
    @Autowired
    private RegistrationRequestDTOMapper registrationRequestDTOMapper;
    @Autowired
    private ServiceProviderRepository serviceProviderRepository;
    @Autowired
    private EventOrganizerRepository eventOrganizerRepository;

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


    public Optional<SimpleAccountDTO> findByEmailAndPassword(LoginDTO loginDTO) {
        Optional<Account> accountOptional = accountRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        return accountOptional.map(accountDTOMapper::fromAccountToSimpleDTO);
    }


    public SimpleAccountDTO findActiveByEmail(String email) {
        Optional<Account> accountOptional = accountRepository.findByIsActiveAndEmail(true,email);
        return accountOptional.map(accountDTOMapper::fromAccountToSimpleDTO).orElse(null);
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
        System.out.println(account.getPerson());
        ProfileForPersonDTO profileForPerson = personService.getProfile(account.getPerson().getId());

        Person person = account.getPerson();
        if (account.getType() == PersonType.SERVICE_PROVIDER) {
            Optional<ServiceProvider> serviceProvider = serviceProviderRepository.findById(person.getId());
            if(serviceProvider.isPresent()){
                profileForPerson.setCompanyEmail(serviceProvider.get().getCompanyEmail());
                profileForPerson.setCompanyName(serviceProvider.get().getCompanyName());
                profileForPerson.setCompanyPhoneNumber(serviceProvider.get().getCompanyPhoneNumber());
                profileForPerson.setCompanyPhotos(serviceProvider.get().getCompanyPhotos());
                profileForPerson.setCompanyDescription(serviceProvider.get().getCompanyDescription());
                SimpleLocationDTO location = new SimpleLocationDTO();
                location.setAddress(serviceProvider.get().getCompanyLocation().getAddress());
                location.setCity(serviceProvider.get().getCompanyLocation().getCity());
                profileForPerson.setCompanyLocation(location);
            }

        } else if (account.getType() == PersonType.EVENT_ORGANIZER) {
            Optional<EventOrganizer> eventOrganizer = eventOrganizerRepository.findById(person.getId());
            if(eventOrganizer.isPresent()){
                Set<Event> events = eventOrganizer.get().getEvents();
                List<SimpleEventDTO> simpleEvents = eventDTOMapper.fromEventListToSimpleDTOList(new ArrayList<>(events));
                profileForPerson.setMyEvents(simpleEvents);
            }
        }
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

    public UpdatedCompanyAccountDTO updateCompanyAccount(UUID id, UpdateCompanyAccountDTO companyAccountDTO){
        Account account = accountRepository.findById(id).orElseGet(null);
        if(account!= null){
            Optional<ServiceProvider> serviceProvider = serviceProviderRepository.findById(account.getPerson().getId());
            if(serviceProvider.isPresent()){
                serviceProvider.get().setCompanyPhoneNumber(companyAccountDTO.getCompanyPhoneNumber());
                serviceProvider.get().setCompanyDescription(companyAccountDTO.getCompanyDescription());
                serviceProvider.get().getCompanyLocation().setAddress(companyAccountDTO.getCompanyLocation().getAddress());
                serviceProvider.get().getCompanyLocation().setCity(companyAccountDTO.getCompanyLocation().getCity());
                serviceProviderRepository.save(serviceProvider.get());
            }
        }
        UpdatedCompanyAccountDTO newCompanyAccount = new UpdatedCompanyAccountDTO();
        Optional<ServiceProvider> newServiceProvider = serviceProviderRepository.findById(account.getPerson().getId());
        if(newServiceProvider.isPresent()){
            newCompanyAccount.setCompanyPhoneNumber(newServiceProvider.get().getCompanyPhoneNumber());
            newCompanyAccount.setCompanyDescription(newServiceProvider.get().getCompanyDescription());
            LocationDTO location = new LocationDTO();
            location.setAddress(newServiceProvider.get().getCompanyLocation().getAddress());
            location.setCity(newServiceProvider.get().getCompanyLocation().getCity());
            newCompanyAccount.setCompanyLocation(location);
        }
        return newCompanyAccount;
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
            if(account.getType() == PersonType.SERVICE_PROVIDER){

            }
            if(account.getType() == PersonType.EVENT_ORGANIZER){
                boolean canBeDeactivated = true;
                EventOrganizer eventOrganizer = eventOrganizerRepository.findById(account.getPerson().getId()).orElseGet(null);
                for(Event event : eventOrganizer.getEvents()){
                    System.out.println(event.getTime().isAfter(LocalDateTime.now()));
                    System.out.println(event.getTime());
                    if(event.getTime().isAfter(LocalDateTime.now())){
                        canBeDeactivated = false;
                    }
                }
                if(!canBeDeactivated){
                    throw new RuntimeException("Event organizer has upcoming events. Can not be deactivated");
                }
            }

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
