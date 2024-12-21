package com.mrsony.localchat.presistancy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "refresh_session")
public class RefreshSession {

    @Id
    private String refreshToken;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "scope")
    private String scope;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;
}
