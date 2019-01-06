package com.ziroom.dao;

import com.ziroom.dto.response.DriverPlanResponse;
import com.ziroom.model.DriverOrderEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface DriverOrderEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DriverOrderEntity record);

    int insertSelective(DriverOrderEntity record);

    DriverOrderEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DriverOrderEntity record);

    int updateByPrimaryKey(DriverOrderEntity record);

    /**
     * @param
     * @return
     * @author codey
     * @description 根据行程单号查询订单
     * @date 2019/1/3 11:10
     */
    DriverOrderEntity selectByDriverNo(String driverNo);

    /**
     * 根据订单好查询
     * @param driverNo
     * @return
     */
    DriverOrderEntity selectByDriverOrderNo(@Param("driverOrderNo") String driverNo);

    int selectTotalAmountByDriverNo(String driverNo);

    DriverOrderEntity findDriverOrderAllInfo(Map<String, Object> map);
}