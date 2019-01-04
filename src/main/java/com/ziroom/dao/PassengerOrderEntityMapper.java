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

    /**
     * @author codey
     * @description 根据司机订单号查询乘客订单
     * @date 2019/1/4 11:14
     * @param
     * @return
     */
    List<PassengerOrderEntity> selectByDriverOrderNo(String orderNo);
}