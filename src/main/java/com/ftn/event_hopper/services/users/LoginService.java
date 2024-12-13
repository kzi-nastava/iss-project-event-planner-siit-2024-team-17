package com.ftn.event_hopper.services.users;

import com.ftn.event_hopper.dtos.users.account.LoginDTO;
import com.ftn.event_hopper.dtos.users.account.SimpleAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {
    @Autowired
    private AccountService accountService;

    public Optional<SimpleAccountDTO> login(LoginDTO loginDTO) {
        return accountService.findByEmailAndPassword(loginDTO);
    }

}
