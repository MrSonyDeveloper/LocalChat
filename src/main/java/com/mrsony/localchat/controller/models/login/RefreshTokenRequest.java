package com.mrsony.localchat.controller.models.login;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class RefreshTokenRequest {
    private String refreshToken;
    private String scope;
}
