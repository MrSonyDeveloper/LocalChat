package com.mrsony.localchat.controller;

import com.mrsony.localchat.controller.models.me.MeResponse;
import com.mrsony.localchat.service.auth.models.UserPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;

public class BaseMeController {

    @GetMapping("/me")
    MeResponse whoAmI(@AuthenticationPrincipal UserPrincipal user) {
        return new MeResponse(
                user.getId(),
                user.getUsername(),
                user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
        );
    }
}
