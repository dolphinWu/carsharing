package com.ziroom.service.user;

import com.ziroom.model.UserEntity;

import java.util.List;

public interface UserService {

    int insertUserInfo(UserEntity user);

    UserEntity getUserInfoById(Integer uId);

    int updateUserByUid(UserEntity userEntity);

    UserEntity getUserInfoByEmployeeNo(String employeeNo);

    /**
     * @author codey
     * @description 增加信用分
     * @date 2019/1/5 11:49
     * @param
     * @return
     */
    void addUserCreditScore(List<String> uidList);

}
