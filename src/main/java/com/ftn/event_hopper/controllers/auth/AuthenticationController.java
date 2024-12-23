package com.ftn.event_hopper.controllers.auth;


import com.ftn.event_hopper.dtos.users.account.LoginDTO;
import com.ftn.event_hopper.dtos.users.account.UserTokenState;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.services.users.LoginService;
import com.ftn.event_hopper.util.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    private LoginService loginService;

//    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> getLoginAccount(@RequestBody LoginDTO loginDTO) {
//        LoginResponse response = loginService.login(loginDTO);
//
//        if (response.isSuccess()) {
//            return ResponseEntity.ok(response); // Successful login
//        }
//
//        // Return error message with BAD_REQUEST status
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getMessage());
//    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserTokenState> getLoginAccount(
            @RequestBody LoginDTO loginDTO) {      //LoginDTO - credentials    HttpServletRequest - manipulating with HTTP response
        System.out.println("Usao");
        //AuthenticationManager automatically uses UserDetailService which has only one method - UserDetails loadUserByUsername(username). It
        //returns username,password and role based on username (not by password)
        //AuthenticationManager compares password entered by client with password in UserDetails class
        //If everything is OK, AuthenticationManager returns Authentication object which represents successfully logged in user, otherwise it throws AuthenticationException

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(), loginDTO.getPassword()
                )
        );

        System.out.println(authentication.getPrincipal());
        System.out.println(authentication.getDetails());

        //seting user into context - info about authenticated user
        SecurityContextHolder.getContext().setAuthentication(authentication);


        //generates token
        Account user = (Account) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user);
        int expiresIn = tokenUtils.getExpiredIn();

        return  ResponseEntity.ok(new UserTokenState(jwt, (long) expiresIn));
    }



}
