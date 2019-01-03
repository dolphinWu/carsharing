package com.ziroom.service.user.impl;

import com.ziroom.dao.UserEntityMapper;
import com.ziroom.exception.BusinessException;
import com.ziroom.model.UserEntity;
import com.ziroom.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Transactional
    @Override
    public int insertUserInfo(UserEntity user) {
//        if (null == user.getId())
//            throw BusinessException.withErrorMsg("用户编号不可能为空");
        return userEntityMapper.insertSelective(user);
    }

    @Override
    public UserEntity getUserInfoById(Integer Id) {
        return userEntityMapper.selectByPrimaryKey(Id);
    }


    public int updateCarNoByEmployeeNo(UserEntity user){
        return userEntityMapper.updateByPrimaryKey(user);
    }

   public UserEntity getUserInfoByEmployeeNo(String employeeNo){
       return userEntityMapper.getUserInfoByEmployeeNo(employeeNo);
    }
}
