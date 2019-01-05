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
    public void addUserFriendshipScore(List<String> uidLis) {
        //先排序
        uidLis.sort((u1, u2) -> Long.parseLong(u1) > Long.parseLong(u2) ? 1 : -1);
        //循环组合关系
        for (int i = 0; i < uidLis.size(); i++) {
            for (int j = i + 1 ; j < uidLis.size(); j++) {
                //封装用户关系model
                UserRelationEntity userRelationEntity = userRelationEntityMapper.selectRelation(uidLis.get(i),uidLis.get(j));
                //判断用户关系十分存在
                if (userRelationEntity == null){
                    //不存在则创建关系
                    UserRelationEntity relationEntity = UserRelationEntity.builder()
                            .uid1(uidLis.get(i))
                            .uid2(uidLis.get(j))
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

}
