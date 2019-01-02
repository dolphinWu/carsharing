package com.ziroom.dao;

import com.ziroom.model.UserRelationEntity;

public interface UserRelationEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRelationEntity record);

    int insertSelective(UserRelationEntity record);

    UserRelationEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRelationEntity record);

    int updateByPrimaryKey(UserRelationEntity record);
}