package com.ziroom.dao;

import com.ziroom.model.DriverOrderEntity;

public interface DriverOrderEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DriverOrderEntity record);

    int insertSelective(DriverOrderEntity record);

    DriverOrderEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DriverOrderEntity record);

    int updateByPrimaryKey(DriverOrderEntity record);

    /**
     * @author codey
     * @description 根据行程单号查询订单
     * @date 2019/1/3 11:10
     * @param
     * @return
     */
    DriverOrderEntity selectByDriverNo(String driverNo);

   int  selectTotalAmountByDriverNo(String uid);
}