package com.ziroom.dao;

import com.ziroom.model.DriverPlanEntity;
import com.ziroom.model.PassengerOrderEntity;

import java.util.List;
import java.util.Map;

public interface PassengerOrderEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PassengerOrderEntity record);

    int insertSelective(PassengerOrderEntity record);

    PassengerOrderEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PassengerOrderEntity record);

    int updateByPrimaryKey(PassengerOrderEntity record);

    List<DriverPlanEntity> getAllDriverPlanInManyHours(Map<String,Object> map);
}