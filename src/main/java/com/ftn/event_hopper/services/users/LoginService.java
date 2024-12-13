package com.ftn.event_hopper.services.users;

import com.ftn.event_hopper.dtos.users.account.LoginDTO;
import com.ftn.event_hopper.dtos.users.account.SimpleAccountDTO;
import com.ftn.event_hopper.mapper.users.AccountDTOMapper;
import com.ftn.event_hopper.models.users.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountDTOMapper accountDTOMapper;

    public Optional<SimpleAccountDTO> login(LoginDTO loginDTO) {
        Optional<Account> account = accountService.findByEmailAndPassword(loginDTO);
        if (account.isPresent() && account.get().isValid()) {
            return Optional.ofNullable(accountDTOMapper.fromAccountToSimpleDTO(account.get()));
        }
        return Optional.empty();
    }
}
