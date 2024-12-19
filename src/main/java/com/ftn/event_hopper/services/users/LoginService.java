package com.ftn.event_hopper.services.users;

import com.ftn.event_hopper.dtos.users.account.LoginDTO;
import com.ftn.event_hopper.dtos.users.account.LoginResponse;
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

    public LoginResponse login(LoginDTO loginDTO) {
        Optional<Account> accountOpt = accountService.findByEmail(loginDTO.getEmail());

        if (accountOpt.isEmpty()) {
            // Account with this email doesn't exist
            return new LoginResponse(false, "Account not found.");
        }

        Account account = accountOpt.get();

        if (!account.getPassword().equals(loginDTO.getPassword())) {
            return new LoginResponse(false, "Incorrect password.");
        }

        if (!account.isVerified()) {
            return new LoginResponse(false, "Account not verified. Please check your email.");
        }

        if (!account.isActive()) {
            return new LoginResponse(false, "Account is inactive.");
        }

        // Successful login
        SimpleAccountDTO simpleAccountDTO = accountDTOMapper.fromAccountToSimpleDTO(account);
        return new LoginResponse(simpleAccountDTO);
    }
}
