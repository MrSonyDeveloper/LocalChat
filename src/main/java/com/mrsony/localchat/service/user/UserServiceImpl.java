package com.mrsony.localchat.service.user;

import com.mrsony.localchat.presistancy.dao.UsersDao;
import com.mrsony.localchat.presistancy.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsersDao usersDao;

    @Override
    @Transactional
    public UserEntity getUserByLogin(String login) {
        return usersDao.findByLoginAndIsDeletedFalse(login);
    }

    @Override
    @Transactional
    public UserEntity getUserById(Long id) {
        return usersDao.findByIdAndIsDeletedFalse(id);
    }
}
