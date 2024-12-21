package com.mrsony.localchat.presistancy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "permissions")
public class PermissionEntity {

    @Id
    @Column(name = "code", nullable = false, insertable = false, updatable = false, unique = true)
    private String code;
}
