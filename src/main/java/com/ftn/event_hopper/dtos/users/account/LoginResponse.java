package com.ftn.event_hopper.dtos.users.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private boolean success;
    private String message;
    private String token;
    private Long expiresIn;

    //for failed login this is all i need
    public LoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
