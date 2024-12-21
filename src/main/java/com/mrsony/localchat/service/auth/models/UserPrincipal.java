package com.mrsony.localchat.service.auth.models;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class UserPrincipal extends User {
    final Long id;

    public UserPrincipal(
            Long id,
            String login,
            String password,
            List<SimpleGrantedAuthority> credentials
    ) {
        super(login, password, credentials);
        this.id = id;
    }
}
