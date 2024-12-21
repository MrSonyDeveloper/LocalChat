package com.mrsony.localchat.service.user;

import com.mrsony.localchat.presistancy.entity.UserEntity;

public interface UserService {

    UserEntity getUserByLogin(String login);

    UserEntity getUserById(Long id);

}
