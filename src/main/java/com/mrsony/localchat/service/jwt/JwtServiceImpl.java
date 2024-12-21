package com.mrsony.localchat.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${app.jwt.secret}")
    private String secret;

    @Override
    public String create(String scope, String subject, Duration duration) {
        return JWT.create()
                .withSubject(subject)
                .withClaim("scope", scope)
                .withExpiresAt(Instant.now().plus(duration))
                .sign(getAlgorithm());
    }

    @Override
    public DecodedJWT verified(String token) {
        return JWT.require(getAlgorithm())
                .build()
                .verify(token);
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC512(secret);
    }
}
