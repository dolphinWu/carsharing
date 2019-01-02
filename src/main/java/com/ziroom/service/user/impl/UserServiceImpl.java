package com.ziroom.service.user.impl;

import com.ziroom.dao.UserDao;
import com.ziroom.exception.BusinessException;
import com.ziroom.model.UserEntity;
import com.ziroom.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;//这里会报错，但是并不会影响

    @Transactional
    @Override
    public int updateUserInfo(UserEntity user) {
        if (null == user.getId())
            throw BusinessException.withErrorMsg("用户编号不可能为空");
        return userDao.updateUserInfo(user);
    }

    @Override
    public UserEntity getUserInfoById(Integer uId) {
        return userDao.getUserInfoById(uId);
    }

}
