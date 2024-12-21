package com.mrsony.localchat.service.auth.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@Getter
@RequiredArgsConstructor
public class TokenData {
    private final String token;
    private final Duration ttl;
}
