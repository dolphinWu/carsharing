package com.ziroom.service.user.impl;

import com.ziroom.dao.UserRelationEntityMapper;
import com.ziroom.model.UserRelationEntity;
import com.ziroom.service.user.UserRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by codey on 2019/1/5.
 */
@Service
public class UserRelationServiceImpl implements UserRelationService {

    @Autowired
    private UserRelationEntityMapper userRelationEntityMapper;

    /**
     * @author codey
     * @description 增加用户亲密度
     * @date 2019/1/5 13:05
     * @param
     * @return
     */
    @Override
    public void addUserFriendshipScore(List<String> uidList) {
        //先排序
        uidList.sort((u1, u2) -> Long.parseLong(u1) > Long.parseLong(u2) ? 1 : -1);
        //循环组合关系
        for (int i = 0; i < uidList.size(); i++) {
            for (int j = i + 1 ; j < uidList.size(); j++) {
                UserRelationEntity userRelationEntity = userRelationEntityMapper.selectRelation(uidList.get(i),uidList.get(j));
                //判断用户关系是否存在
                if (userRelationEntity == null){
                    //不存在则创建关系
                    UserRelationEntity relationEntity = UserRelationEntity.builder()
                            .uid1(uidList.get(i))
                            .uid2(uidList.get(j))
                            .friendshipScore(1)
                            .build();
                    relationEntity.setCreateDate(new Date());
                    relationEntity.setLastModifyDate(new Date());
                    relationEntity.setCreateUser("system");
                    relationEntity.setLastModifyUser("system");
                    //保存
                    userRelationEntityMapper.insert(relationEntity);
                } else {
                    userRelationEntity.setFriendshipScore(userRelationEntity.getFriendshipScore() + 1);
                    userRelationEntity.setLastModifyUser("system");
                    userRelationEntity.setLastModifyDate(new Date());
                    //修改
                    userRelationEntityMapper.updateByPrimaryKeySelective(userRelationEntity);
                }

            }
        }
    }

    /**
     * @author codey
     * @description 扣除司机与乘客的亲密度
     * @date 2019/1/5 15:33
     * @param
     * @return
     */
    @Override
    public void deductDriverFriendshipScore(String driverUid,List<String> passengerUidList) {
        //先放一起排序
        List<String> uidList = new ArrayList<>();
        uidList.add(driverUid);
        uidList.addAll(passengerUidList);
        uidList.sort((u1, u2) -> Long.parseLong(u1) > Long.parseLong(u2) ? 1 : -1);
        //循环组合关系
        for (int i = 0; i < uidList.size(); i++) {
            for (int j = i + 1; j < uidList.size(); j++) {
                //只有是跟司机有关系的组合才需要扣除
                if(uidList.get(i).equals(driverUid) || uidList.get(j).equals(driverUid) ){
                    //判断关系是否存在
                    UserRelationEntity userRelationEntity = userRelationEntityMapper.selectRelation(uidList.get(i),uidList.get(j));
                    if(userRelationEntity != null){
                        //存在关系才扣除
                        userRelationEntity.setFriendshipScore(userRelationEntity.getFriendshipScore() - 1);
                        userRelationEntity.setLastModifyUser("system");
                        userRelationEntity.setLastModifyDate(new Date());
                        //修改
                        userRelationEntityMapper.updateByPrimaryKeySelective(userRelationEntity);
                    }
                }
            }
        }
    }

}
