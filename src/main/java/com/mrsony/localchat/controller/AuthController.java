package com.mrsony.localchat.controller;

import com.mrsony.localchat.controller.models.login.LoginRequest;
import com.mrsony.localchat.controller.models.login.LogoutRequest;
import com.mrsony.localchat.controller.models.login.RefreshTokenRequest;
import com.mrsony.localchat.controller.models.login.TokenResponse;
import com.mrsony.localchat.service.auth.AuthService;
import com.mrsony.localchat.service.auth.models.TokenInfo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final String REFRESH_COOKIE_NAME = "Refresh-Token";

    private final AuthService authService;

    @PostMapping("login")
    ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        TokenInfo tokenInfo =
                authService.login(
                        request.getScope(),
                        request.getUsername(),
                        request.getPassword()
                );
        return buildResponse(tokenInfo, response);
    }

    @PostMapping("refresh-token")
    ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request,
                                   // @CookieValue(name = REFRESH_COOKIE_NAME) String refreshCookieToken,
                                   HttpServletResponse response) {
        String refreshToken =
                switch (request.getScope()) {
                    case "mobile" -> request.getRefreshToken();
                    // case "web" -> refreshCookieToken;
                    default ->
                            throw new IllegalArgumentException("Scope '" + request.getScope() + "' is not supported");
                };

        TokenInfo refreshed =
                authService.refreshAccessToken(request.getScope(), refreshToken);
        return buildResponse(refreshed, response);
    }

    @PostMapping("logout")
    ResponseEntity<?> logout(@RequestBody LogoutRequest request,
                             @CookieValue(name = REFRESH_COOKIE_NAME) String refreshCookieToken,
                             HttpServletResponse response) {
        String refreshToken =
                switch (request.getScope()) {
                    case "mobile" -> request.getRefreshToken();
//                    case "web" -> {
//                        setRefreshCookie(response,
//                                null,
//                                null
//                        );
//                        yield refreshCookieToken;
//                    }
                    default ->
                            throw new IllegalArgumentException("Scope '" + request.getScope() + "' is not supported");
                };

        authService.logout(request.getScope(), refreshToken);

        return ResponseEntity.noContent().build();
    }

    ResponseEntity<?> buildResponse(TokenInfo tokenInfo, HttpServletResponse response) {
        if (tokenInfo.getScope().equals("mobile")) {
            return ResponseEntity.ok(
                    new TokenResponse(
                            tokenInfo.getAccess().getToken(),
                            tokenInfo.getRefresh() != null ?
                                    tokenInfo.getRefresh().getToken() :
                                    null
                    )
            );
        }
        throw new IllegalArgumentException("Unsupported scope " + tokenInfo.getScope());

//        else {
//            assert tokenInfo.getRefresh() != null;
//
//            setRefreshCookie(response,
//                    tokenInfo.getRefresh().getToken(),
//                    tokenInfo.getRefresh().getTtl()
//            );
//
//            return ResponseEntity.ok(
//                    new TokenResponse(
//                            tokenInfo.getAccess().getToken(),
//                            null
//                    )
//            );
//
//        }
    }
//
//    private void setRefreshCookie(HttpServletResponse response, String refreshToken, Duration ttl) {
//        Cookie refreshCookie = new Cookie(REFRESH_COOKIE_NAME, refreshToken);
//        refreshCookie.setPath("/api/auth");
//        refreshCookie.setHttpOnly(true);
//        refreshCookie.setMaxAge(
//                refreshToken != null ?
//                        (int) ttl.toSeconds()
//                        : 0
//        );
//
//        response.addCookie(
//                refreshCookie
//        );
//    }
}
