package com.ftn.event_hopper.controllers.auth;


import com.ftn.event_hopper.dtos.users.account.LoginDTO;
import com.ftn.event_hopper.dtos.users.account.LoginResponse;
import com.ftn.event_hopper.dtos.users.account.SimpleAccountDTO;
import com.ftn.event_hopper.services.users.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "*")
public class AuthenticationController {
    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLoginAccount(@RequestBody LoginDTO loginDTO) {
        LoginResponse response = loginService.login(loginDTO);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response.getAccount()); // Successful login
        }

        // Return error message with BAD_REQUEST status
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getMessage());
    }



}
