package com.ziroom.dao;

import com.ziroom.model.AddressEntity;

import java.util.List;

public interface AddressEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AddressEntity record);

    int insertSelective(AddressEntity record);

    AddressEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AddressEntity record);

    int updateByPrimaryKey(AddressEntity record);

    List<AddressEntity> selectListByUid(String uid);
}