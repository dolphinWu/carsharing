package com.ziroom.dao;

import com.ziroom.model.AddressEntity;

public interface AddressEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AddressEntity record);

    int insertSelective(AddressEntity record);

    AddressEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AddressEntity record);

    int updateByPrimaryKey(AddressEntity record);
}