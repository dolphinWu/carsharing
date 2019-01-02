package com.ziroom.dao;

import com.ziroom.model.PassengerOrderEntity;

public interface PassengerOrderEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PassengerOrderEntity record);

    int insertSelective(PassengerOrderEntity record);

    PassengerOrderEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PassengerOrderEntity record);

    int updateByPrimaryKey(PassengerOrderEntity record);
}