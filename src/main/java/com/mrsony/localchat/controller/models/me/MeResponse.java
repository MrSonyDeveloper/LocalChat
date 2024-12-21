package com.mrsony.localchat.controller.models.me;


import java.util.List;

public record MeResponse(
        Long id,
        String login,
        List<String> permissions
) {
}
