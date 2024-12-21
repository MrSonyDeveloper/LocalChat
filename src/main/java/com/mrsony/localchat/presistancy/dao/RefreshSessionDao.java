package com.mrsony.localchat.presistancy.dao;

import com.mrsony.localchat.presistancy.entity.RefreshSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshSessionDao extends JpaRepository<RefreshSession, String> {
}