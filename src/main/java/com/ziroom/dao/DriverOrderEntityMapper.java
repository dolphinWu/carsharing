package com.ziroom.dao;

import com.ziroom.model.DriverOrderEntity;

public interface DriverOrderEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DriverOrderEntity record);

    int insertSelective(DriverOrderEntity record);

    DriverOrderEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DriverOrderEntity record);

    int updateByPrimaryKey(DriverOrderEntity record);
}