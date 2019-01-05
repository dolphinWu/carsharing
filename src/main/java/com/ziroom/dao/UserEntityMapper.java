package com.ziroom.dao;

import com.ziroom.model.UserEntity;

public interface UserEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);

    UserEntity getUserInfoByEmployeeNo(String employeeNo);

    /**
     * @author codey
     * @description 增加用户信用分
     * @date 2019/1/5 11:33
     * @param
     * @return
     */
    void addUserCreditScore(String uid);

    UserEntity selectByUId(Integer uId);

    /**
     * @author codey
     * @description 扣除用户信用分
     * @date 2019/1/5 15:26
     * @param
     * @return
     */
    void deductUserCreditScore(String uid);
}