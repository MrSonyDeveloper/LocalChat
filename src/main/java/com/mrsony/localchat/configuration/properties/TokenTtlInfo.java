package com.mrsony.localchat.configuration.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter
@RequiredArgsConstructor
public class TokenTtlInfo {
    private Duration access;
    private Duration refresh;
}