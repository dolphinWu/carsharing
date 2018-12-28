package com.ziroom.dao;

import com.ziroom.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {

    /**
     * @Description: 更改用户信息
     * @param user
     */
    int updateUserInfo(UserEntity user);

    /**
     * @Description: 根据主键编号获取用户信息
     * @param uId 主键
     */
    UserEntity getUserInfoById(@Param("uid") Integer uId);

    /**
     * 根据用户名和密码获取用户信息
     * @param username
     * @param password
     * @return
     */
    UserEntity getUserInfoByCond(@Param("username") String username, @Param("password") String password);

}
