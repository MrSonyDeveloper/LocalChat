package com.mrsony.localchat.service.auth;

import com.mrsony.localchat.service.auth.models.TokenInfo;

public interface AuthService {

    TokenInfo login(String scope, String login, String password);

    TokenInfo refreshAccessToken(String scope, String refreshToken);

    void logout(String scope, String refreshToken);
}
