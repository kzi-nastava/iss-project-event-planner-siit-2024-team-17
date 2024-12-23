package com.ftn.event_hopper.dtos.users.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

public class UserTokenState {
    private String accessToken;
    private Long expiresIn;

    public UserTokenState() {
        this.accessToken = null;
        this.expiresIn = null;
    }

}
