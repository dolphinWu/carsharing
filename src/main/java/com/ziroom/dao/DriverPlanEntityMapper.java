package com.ziroom.dao;

import com.ziroom.model.DriverPlanEntity;

public interface DriverPlanEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DriverPlanEntity record);

    int insertSelective(DriverPlanEntity record);

    DriverPlanEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DriverPlanEntity record);

    int updateByPrimaryKey(DriverPlanEntity record);
}