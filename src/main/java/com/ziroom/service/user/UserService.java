package com.ziroom.service.user;

import com.ziroom.model.UserEntity;

public interface UserService {

    int insertUserInfo(UserEntity user);

    UserEntity getUserInfoById(Integer uId);

    int updateCarNoByEmployeeNo(UserEntity userEntity);

    UserEntity getUserInfoByEmployeeNo(String employeeNo);

}
