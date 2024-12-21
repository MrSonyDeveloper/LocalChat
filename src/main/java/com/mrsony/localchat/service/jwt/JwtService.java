package com.mrsony.localchat.service.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.Duration;

public interface JwtService {

    String create(String scope, String subject, Duration duration);

    DecodedJWT verified(String token);
}
