package com.ziroom.service.user;

import com.ziroom.model.UserEntity;

public interface UserService {

    int updateUserInfo(UserEntity user);

    UserEntity getUserInfoById(Integer uId);

}
