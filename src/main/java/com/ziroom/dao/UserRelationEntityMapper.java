package com.ziroom.dao;

import com.ziroom.model.UserRelationEntity;
import org.apache.ibatis.annotations.Param;

public interface UserRelationEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRelationEntity record);

    int insertSelective(UserRelationEntity record);

    UserRelationEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRelationEntity record);

    int updateByPrimaryKey(UserRelationEntity record);

    /**
     * @author codey
     * @description 查询用户关系
     * @date 2019/1/5 13:38
     * @param
     * @return
     */
    UserRelationEntity selectRelation(@Param("uid1") String uid1, @Param("uid2") String uid2);
}