package com.ziroom.service.user.impl;

import com.ziroom.dao.UserEntityMapper;
import com.ziroom.model.UserEntity;
import com.ziroom.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public UserEntity getUserInfoByUId(Integer uId) {
        return userEntityMapper.selectByUId(uId);
    }


    public int updateUserByUid(UserEntity user){
        return userEntityMapper.updateByPrimaryKeySelective(user);
    }

   public UserEntity getUserInfoByEmployeeNo(String employeeNo){
       return userEntityMapper.getUserInfoByEmployeeNo(employeeNo);
    }

    /**
     * @author codey
     * @description 增加用户信用分
     * @date 2019/1/5 11:32
     * @param
     * @return
     */
    @Override
    public void addUserCreditScore(List<String> uidList) {
        uidList.forEach(uid ->{
            userEntityMapper.addUserCreditScore(uid);
        });
    }

    /**
     * @author codey
     * @description 扣除用户信用分
     * @date 2019/1/5 15:24
     * @param
     * @return
     */
    @Override
    public void deductUserCreditScore(String... uidList) {
        for (String uid : uidList) {
            userEntityMapper.deductUserCreditScore(uid);
        }
    }

}
