package com.ftn.event_hopper.controllers.auth;

import com.ftn.event_hopper.dtos.users.account.LoginDTO;
import com.ftn.event_hopper.dtos.users.account.LoginResponse;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.services.users.AccountService;
import com.ftn.event_hopper.util.TokenUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private AuthenticationManager authenticationManager;            //processes authentication
    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> createAuthenticationToken(
            @RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        Optional<Account> accountOpt = accountService.findByEmail(loginDTO.getEmail());
        if (accountOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new LoginResponse(false, "Account not found."));
        }
        Account account = accountOpt.get();

        if (!account.isVerified()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new LoginResponse(false, "Account is not verified. Please check your email."));
        }
        if (!account.isActive()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new LoginResponse(false, "Account has been deactivated."));
        }

        //Authenticate
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getEmail(), loginDTO.getPassword()
                    )
            );

            //seting user into context - info about authenticated user
            SecurityContextHolder.getContext().setAuthentication(authentication);


            //generates token
            Account user = (Account) authentication.getPrincipal();
            String jwt = tokenUtils.generateToken(user);
            int expiresIn = tokenUtils.getExpiredIn();

            return ResponseEntity.ok(new LoginResponse(true, "Login successful.", jwt, (long) expiresIn));

        } catch (Exception e) {
            // Incorrect password
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(false, "Incorrect password."));
        }
    }



}
