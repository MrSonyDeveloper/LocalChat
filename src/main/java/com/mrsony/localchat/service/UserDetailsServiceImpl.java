package com.mrsony.localchat.service;

import com.mrsony.localchat.presistancy.entity.UserEntity;
import com.mrsony.localchat.service.auth.models.UserPrincipal;
import com.mrsony.localchat.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userService.getUserByLogin(username);

        if (userEntity == null) {
            throw new UsernameNotFoundException("User with login: " + username + " not found");
        }

        List<SimpleGrantedAuthority> authorities =
                userEntity.getPermissions()
                        .stream()
                        .map(permission ->
                                new SimpleGrantedAuthority(permission.getCode())
                        )
                        .toList();

        return new UserPrincipal(
                userEntity.getId(),
                userEntity.getLogin(),
                userEntity.getPassword(),
                authorities
        );
    }
}
