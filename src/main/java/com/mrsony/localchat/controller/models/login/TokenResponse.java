package com.mrsony.localchat.controller.models.login;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
