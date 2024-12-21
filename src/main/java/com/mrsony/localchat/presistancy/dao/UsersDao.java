package com.mrsony.localchat.presistancy.dao;

import com.mrsony.localchat.presistancy.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersDao extends JpaRepository<UserEntity, Long> {

    UserEntity findByLoginAndIsDeletedFalse(String username);

    UserEntity findByIdAndIsDeletedFalse(Long id);

}
