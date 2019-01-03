package com.ziroom.service.driverOrder.impl;

import com.ziroom.constant.DriverPlanStatus;
import com.ziroom.dao.DriverOrderEntityMapper;
import com.ziroom.dao.DriverPlanEntityMapper;
import com.ziroom.dto.request.DriverPlanRequest;
import com.ziroom.dto.response.DriverPlanResponse;
import com.ziroom.model.DriverOrderEntity;
import com.ziroom.model.DriverPlanEntity;
import com.ziroom.service.driverOrder.DriverPlanService;
import com.ziroom.utils.APIResponse;
import com.ziroom.utils.DateKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by codey on 2019/1/2.
 */
@Service
public class DriverPlanServiceImpl implements DriverPlanService {

    private static Logger logger = LoggerFactory.getLogger(DriverPlanServiceImpl.class);

    @Autowired
    private DriverPlanEntityMapper driverPlanEntityMapper;
    @Autowired
    private DriverOrderEntityMapper driverOrderEntityMapper;

    //1.查询历史记录;2.行程单发布后需要查询分布的行程单（状态为拼车中）
    @Override
    public APIResponse getHistoryPlan(String driverUid,Integer status) {
        List<DriverPlanEntity> list = driverPlanEntityMapper.selectByUid(driverUid,status);
        List<DriverPlanResponse> responselist = new ArrayList<>();
        for (DriverPlanEntity driverPlanEntity : list) {
            DriverPlanResponse response = new DriverPlanResponse();
            BeanUtils.copyProperties(driverPlanEntity,response);
            //查询该行程单的订单
            DriverOrderEntity driverOrderEntity = driverOrderEntityMapper.selectByDriverNo(driverPlanEntity.getDriverNo());
            //设置当前拼车乘客数
            response.setPassengerCount(driverOrderEntity.getPassengerCount());
            responselist.add(response);
        }
        return APIResponse.success(responselist);
    }

    //创建行程单
    @Override
    public APIResponse createDriverPlan(DriverPlanRequest driverPlanReq) {
        DriverPlanEntity driverPlan = new DriverPlanEntity();
        BeanUtils.copyProperties(driverPlanReq,driverPlan);
        //状态
        driverPlan.setStatus(DriverPlanStatus.WAITING.getCode());
        //单号
        driverPlan.setDriverNo("DP"+System.currentTimeMillis());
        driverPlan.setCreateTime(new Date());
        driverPlan.setUpdateTime(new Date());
        driverPlan.setIsDel(0);
        driverPlanEntityMapper.insert(driverPlan);
        return APIResponse.success();
    }

    //开始行程，返回起点终点的经纬度（导航）
    @Override
    public APIResponse beginPlan(DriverPlanRequest driverPlanReq) {
        //判断是否到时间
        if(!DateKit.compareDate(DateKit.dateFormat(driverPlanReq.getPlanStartTime()),DateKit.dateFormat(new Date()))){
            return APIResponse.fail("未到开始时间，不可开始。");
        }
        DriverPlanEntity driverPlan = new DriverPlanEntity();
        BeanUtils.copyProperties(driverPlanReq,driverPlan);
        //修改行程单状态
        driverPlan.setStatus(DriverPlanStatus.DRIVING.getCode());
        driverPlanEntityMapper.updateByPrimaryKey(driverPlan);
        //修改订单状态

        return APIResponse.success(driverPlan);
    }

    //取消行程单
    @Override
    public APIResponse cancelPlan(DriverPlanRequest driverPlanReq) {
        DriverPlanEntity driverPlan = new DriverPlanEntity();
        BeanUtils.copyProperties(driverPlanReq,driverPlan);
        //1.行程单的状态：取消
        driverPlan.setStatus(DriverPlanStatus.CANCEL.getCode());
        driverPlanEntityMapper.updateByPrimaryKey(driverPlan);
        //2.订单的状态：

        //3.
        return APIResponse.success();
    }

    //确认结束，完成行程
    @Override
    public APIResponse finishPlan(DriverPlanRequest driverPlanReq) {
        //更新订单实际金额、状态
        DriverOrderEntity driverOrderEntity = driverOrderEntityMapper.selectByDriverNo(driverPlanReq.getDriverNo());
        driverOrderEntity.setActualAmount(new BigDecimal(driverPlanReq.getActualAmount()).multiply(new BigDecimal("100")).intValue());
        //driverOrderEntity.setStatus();
        return APIResponse.success();
    }

    @Override
    public DriverPlanEntity findDriverPlanById(Integer id) {
        return driverPlanEntityMapper.selectByPrimaryKey(id);
    }
}
