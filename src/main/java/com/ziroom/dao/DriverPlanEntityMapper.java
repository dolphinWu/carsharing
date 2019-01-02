package com.ziroom.dao;

import com.ziroom.model.DriverPlanEntity;

import java.util.List;

public interface DriverPlanEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DriverPlanEntity record);

    int insertSelective(DriverPlanEntity record);

    DriverPlanEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DriverPlanEntity record);

    int updateByPrimaryKey(DriverPlanEntity record);

    /**
     * @author codey
     * @description 根据司机uid查询全部
     * @date 2019/1/2 17:55
     * @param
     * @return
     */
    List<DriverPlanEntity> selectByUid(String driverUid);
}