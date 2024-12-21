package com.mrsony.localchat.service.auth.models;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TokenInfo {
    private final String scope;
    private final TokenData access;
    @Nullable
    private final TokenData refresh;

   public TokenInfo(String scope, TokenData access) {
       this.scope = scope;
       this.access = access;
       this.refresh = null;
   }
}
