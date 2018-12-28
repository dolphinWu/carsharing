package com.ziroom.service.user.impl;

import com.ziroom.constant.ErrorConstant;
import com.ziroom.dao.UserDao;
import com.ziroom.exception.BusinessException;
import com.ziroom.model.UserEntity;
import com.ziroom.service.user.UserService;
import com.ziroom.utils.TaleUtils;
import org.apache.commons.lang3.StringUtils;
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
            throw BusinessException.withErrorCode("用户编号不可能为空");
        return userDao.updateUserInfo(user);
    }

    @Override
    public UserEntity getUserInfoById(Integer uId) {
        return userDao.getUserInfoById(uId);
    }

    @Override
    public UserEntity login(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password))
            throw BusinessException.withErrorCode(ErrorConstant.Auth.USERNAME_PASSWORD_IS_EMPTY);

        String pwd = TaleUtils.MD5encode(username + password);
        UserEntity user = userDao.getUserInfoByCond(username, pwd);
        if (null == user)
            throw BusinessException.withErrorCode(ErrorConstant.Auth.USERNAME_PASSWORD_ERROR);

        return user;
    }


}
