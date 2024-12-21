package com.mrsony.localchat.service.auth;

import com.mrsony.localchat.configuration.properties.AuthConfigurationProperties;
import com.mrsony.localchat.configuration.properties.TokenTtlInfo;
import com.mrsony.localchat.presistancy.dao.RefreshSessionDao;
import com.mrsony.localchat.presistancy.entity.RefreshSession;
import com.mrsony.localchat.presistancy.entity.UserEntity;
import com.mrsony.localchat.service.auth.models.TokenData;
import com.mrsony.localchat.service.auth.models.TokenInfo;
import com.mrsony.localchat.service.auth.models.UserPrincipal;
import com.mrsony.localchat.service.jwt.JwtService;
import com.mrsony.localchat.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthConfigurationProperties authConfigurationProperties;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    private final RefreshSessionDao refreshSessionDao;

    @Override
    @Transactional
    public TokenInfo login(String scope, String login, String password) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                login, password
                        )
                );

        assert authentication.isAuthenticated();

        TokenTtlInfo ttl = getTtl(scope);

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        final LocalDateTime now = LocalDateTime.now();

        RefreshSession session = new RefreshSession();
        session.setUserId(principal.getId());
        session.setScope(scope);
        session.setRefreshToken(UUID.randomUUID().toString());
        session.setCreatedAt(now);
        session.setExpiredAt(now.plus(ttl.getRefresh()));
        refreshSessionDao.save(session);

        return new TokenInfo(
                scope,
                new TokenData(
                        jwtService.create(
                                scope,
                                login,
                                ttl.getAccess()
                        ),
                        ttl.getAccess()
                ),
                new TokenData(
                        session.getRefreshToken(),
                        ttl.getRefresh()
                )
        );
    }

    @Override
    @Transactional
    public TokenInfo refreshAccessToken(String scope, String refreshToken) {
        RefreshSession session = refreshSessionDao.findById(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        if (session.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Refresh token expired");
        }

        TokenTtlInfo ttl = getTtl(scope);

        final LocalDateTime now = LocalDateTime.now();

        RefreshSession newSession = new RefreshSession();
        newSession.setUserId(session.getUserId());
        newSession.setScope(scope);
        newSession.setRefreshToken(UUID.randomUUID().toString());
        newSession.setCreatedAt(now);
        newSession.setExpiredAt(now.plus(ttl.getRefresh()));
        refreshSessionDao.save(newSession);

        refreshSessionDao.delete(session);

        UserEntity user = userService.getUserById(session.getUserId());

        return new TokenInfo(
                scope,
                new TokenData(
                        jwtService.create(
                                scope,
                                user.getLogin(),
                                ttl.getAccess()
                        ),
                        ttl.getAccess()
                ),
                new TokenData(
                        newSession.getRefreshToken(),
                        ttl.getRefresh()
                )
        );
    }

    @Override
    @Transactional
    public void logout(String scope, String refreshToken) {
        refreshSessionDao.deleteById(refreshToken);
    }

    private TokenTtlInfo getTtl(String scope) {
        switch (scope) {
            case "mobile" -> {
                return authConfigurationProperties.getMobile();
            }
            default -> {
                throw new IllegalArgumentException("Unsupported scope " + scope);
            }
        }
    }
}
