package com.mrsony.localchat.controller.models.login;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LoginRequest {
    private String scope;
    private String username;
    private String password;
}
