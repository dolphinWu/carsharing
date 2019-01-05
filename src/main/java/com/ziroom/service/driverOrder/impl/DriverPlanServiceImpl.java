package com.ziroom.service.driverOrder.impl;

import com.ziroom.constant.DriverOrderStatus;
import com.ziroom.constant.DriverPlanStatus;
import com.ziroom.constant.PassengerOrderStatus;
import com.ziroom.dao.DriverOrderEntityMapper;
import com.ziroom.dao.DriverPlanEntityMapper;
import com.ziroom.dao.PassengerOrderEntityMapper;
import com.ziroom.dto.request.DriverPlanRequest;
import com.ziroom.dto.request.PassengerRequest;
import com.ziroom.dto.response.DriverPlanResponse;
import com.ziroom.model.DriverOrderEntity;
import com.ziroom.model.DriverPlanEntity;
import com.ziroom.model.PassengerOrderEntity;
import com.ziroom.service.driverOrder.DriverPlanService;
import com.ziroom.utils.APIResponse;
import com.ziroom.utils.DateKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

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
    @Autowired
    private PassengerOrderEntityMapper passengerOrderEntityMapper;

    @Override
    public List<DriverPlanEntity> getAllDriverPlanInManyHours(PassengerRequest passengerRequest) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("departTime",passengerRequest.getDepartTime());
        return driverPlanEntityMapper.getAllDriverPlanInManyHours(paramMap);
    }

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
            if(driverOrderEntity != null){
                //设置当前拼车乘客数
                response.setPassengerCount(driverOrderEntity.getPassengerCount());
            }else {
                //发布后查看时如果没有订单，显示实际乘车人数为0
                response.setPassengerCount(0);
            }
            responselist.add(response);
        }
        return APIResponse.success(responselist);
    }

    //创建行程单
    @Override
    public APIResponse createDriverPlan(DriverPlanRequest driverPlanReq) {
        //判断行程单是否存在，转态为拼车中和已出发
        List<DriverPlanEntity> list = driverPlanEntityMapper.selectByUidAndStatus(driverPlanReq.getDriverUid());
        if(list.size() != 0){
            return APIResponse.fail("已存在行程单，不可重复发布");
        }

        DriverPlanEntity driverPlan = new DriverPlanEntity();
        BeanUtils.copyProperties(driverPlanReq,driverPlan);
        //状态
        driverPlan.setStatus(DriverPlanStatus.WAITING.getCode());
        //转换金额
        driverPlan.setPlanAmount(new BigDecimal(driverPlanReq.getPlanAmount()).multiply(new BigDecimal("100")).intValue());
        //单号
        driverPlan.setDriverNo("DP"+System.currentTimeMillis());
        driverPlan.setCreateDate(new Date());
        driverPlan.setLastModifyDate(new Date());
        driverPlan.setIsDel(0);
        driverPlanEntityMapper.insert(driverPlan);
        return APIResponse.success(driverPlan);
    }

    //开始行程，返回起点终点的经纬度（导航）
    @Override
    public APIResponse beginPlan(String driverNo) {
        //查询行程单
        DriverPlanEntity driverPlan = driverPlanEntityMapper.selectByNo(driverNo);
        //判断是否到时间
        if(!DateKit.compareDate(DateKit.dateFormat(driverPlan.getPlanStartTime()),DateKit.dateFormat(new Date()))){
            return APIResponse.fail("未到开始时间，不可开始。");
        }
        //修改行程单状态
        driverPlan.setStatus(DriverPlanStatus.DRIVING.getCode());
        driverPlanEntityMapper.updateByPrimaryKey(driverPlan);
        //查询订单
        DriverOrderEntity driverOrder = driverOrderEntityMapper.selectByDriverNo(driverPlan.getDriverNo());
        if(driverOrder == null){
            return APIResponse.success(driverPlan);
        }
        //更新订单状态--行驶中
        driverOrder.setStatus(DriverOrderStatus.TRAVELING.getCode());
        //更新订单实际出发时间
        driverOrder.setActualStartTime(new Date());
        driverOrderEntityMapper.updateByPrimaryKey(driverOrder);

        //TODO 需测试有实际订单但是没有乘客订单的情况
        //查询乘客订单
        List<PassengerOrderEntity> passengerOrderList = passengerOrderEntityMapper.selectByDriverOrderNo(driverOrder.getOrderNo());
        passengerOrderList.forEach(
                passengerOrderEntity -> {
                    //更新状态为行驶中，实际出发时间
                    passengerOrderEntity.setStatus(PassengerOrderStatus.TRAVELING.getStatusCode());
                    passengerOrderEntity.setActualStartTime(new Date());
                    passengerOrderEntityMapper.updateByPrimaryKey(passengerOrderEntity);
                }
        );

        return APIResponse.success(driverPlan);
    }

    //取消行程单
    @Override
    public APIResponse cancelPlan(String driverNo) {
        //查询行程单
        DriverPlanEntity driverPlan = driverPlanEntityMapper.selectByNo(driverNo);
        //只有拼车中可以取消
        if(DriverPlanStatus.WAITING.getCode() != driverPlan.getStatus()){
            return APIResponse.fail("只有拼车中才可以取消哦~");
        }
        //没有订单，直接取消行程单
        driverPlan.setStatus(DriverPlanStatus.CANCEL.getCode());
        driverPlanEntityMapper.updateByPrimaryKey(driverPlan);
        //查询司机订单
        DriverOrderEntity driverOrder = driverOrderEntityMapper.selectByDriverNo(driverNo);
        if(driverOrder == null){
            return APIResponse.success("取消成功！");
        }
        //只有待发车和已满员状态可以取消
        if(DriverOrderStatus.WAIT_DEPART.getCode() != driverOrder.getStatus() && DriverOrderStatus.READY.getCode() != driverOrder.getStatus()){
            return APIResponse.fail("现在不可以取消哦~");
        }
        //更新司机订单状态为取消
        driverOrder.setStatus(DriverOrderStatus.CANCEL.getCode());
        driverOrderEntityMapper.updateByPrimaryKey(driverOrder);
        //查询乘客端订单
        List<PassengerOrderEntity> passengerOrderList = passengerOrderEntityMapper.selectByDriverOrderNo(driverOrder.getOrderNo());
        passengerOrderList.forEach(passengerOrderEntity -> {
            //更新乘客订单状态为取消
            passengerOrderEntity.setStatus(PassengerOrderStatus.CANCEL.getStatusCode());
            passengerOrderEntityMapper.updateByPrimaryKey(passengerOrderEntity);
        });

        return APIResponse.success("取消成功！");
    }

    //确认结束，完成行程
    @Override
    public APIResponse finishPlan(DriverPlanRequest driverPlanReq) {
        //TODO 校验是否在终点一定范围内
        //查询行程单
        DriverPlanEntity driverPlan = driverPlanEntityMapper.selectByNo(driverPlanReq.getDriverNo());
        driverPlan.setStatus(DriverPlanStatus.FINISH.getCode());
        driverPlanEntityMapper.updateByPrimaryKey(driverPlan);

        //查询司机订单
        DriverOrderEntity driverOrder = driverOrderEntityMapper.selectByDriverNo(driverPlan.getDriverNo());
        //判断是否有实际订单
        if(driverOrder == null){
            return APIResponse.success();
        }
        driverOrder.setStatus(DriverOrderStatus.FINISH.getCode());
        driverOrder.setActualEndTime(new Date());
        driverOrderEntityMapper.updateByPrimaryKey(driverOrder);
        //查询乘客端订单
        List<PassengerOrderEntity> passengerOrderList = passengerOrderEntityMapper.selectByDriverOrderNo(driverOrder.getOrderNo());
        passengerOrderList.forEach(passengerOrderEntity -> {
            //更新乘客订单状态为取消
            passengerOrderEntity.setStatus(PassengerOrderStatus.COMPLETE.getStatusCode());
            passengerOrderEntity.setActualEndTime(new Date());
            passengerOrderEntityMapper.updateByPrimaryKey(passengerOrderEntity);
        });
        return APIResponse.success();
    }

    @Override
    public DriverPlanEntity findDriverPlanById(Integer id) {
        return driverPlanEntityMapper.selectByPrimaryKey(id);
    }
}
