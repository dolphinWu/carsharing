package com.ziroom.service.driverOrder.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import com.ziroom.service.user.UserRelationService;
import com.ziroom.service.user.UserService;
import com.ziroom.utils.APIResponse;
import com.ziroom.utils.DateKit;
import com.ziroom.utils.Tools;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private UserService userService;
    @Autowired
    private UserRelationService userRelationService;

    @Override
    public List<DriverPlanEntity> getAllDriverPlanInManyHours(PassengerRequest passengerRequest) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("departTime",passengerRequest.getDepartTime());
        return driverPlanEntityMapper.getAllDriverPlanInManyHours(paramMap);
    }

    //查看发布的行程单--司机首页
    @Override
    public APIResponse getShowPlan(String driverUid) {
        DriverPlanEntity driverPlanEntity = driverPlanEntityMapper.selectByUidAndStatus(driverUid,0);
        //没有也返回
        if(driverPlanEntity == null){
            return APIResponse.success();
        }
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
        return APIResponse.success(response);
    }

    //1.查询历史记录;2.行程单发布后需要查询分布的行程单（状态为拼车中）
    @Override
    public APIResponse getHistoryPlan(String driverUid,Integer pageSize,Integer pageNumber) {
        List<DriverPlanEntity> list = driverPlanEntityMapper.selectByUid(driverUid);
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
        PageHelper.startPage(pageNumber, pageSize, true);
        return APIResponse.success(new PageInfo<>(responselist));
    }

    //创建行程单
    @Override
    public APIResponse createDriverPlan(DriverPlanRequest driverPlanReq) {
        if (driverPlanReq.getCarCapacity() <= 0 || driverPlanReq.getCarCapacity() > 4) {
            return APIResponse.fail("发布乘车座位数有误，请重新下单");
        }
        //判断行程单是否存在，转态为拼车中和已出发
        List<DriverPlanEntity> list = driverPlanEntityMapper.selectExistPlan(driverPlanReq.getDriverUid());
        if(list.size() != 0){
            return APIResponse.fail("已存在行程单，不可重复发布");
        }

        DriverPlanEntity driverPlan = new DriverPlanEntity();
        BeanUtils.copyProperties(driverPlanReq,driverPlan);
        //状态
        driverPlan.setStatus(DriverPlanStatus.WAITING.getCode());
        //转换金额
        //driverPlan.setPlanAmount(new BigDecimal(driverPlanReq.getPlanAmount()).multiply(new BigDecimal("100")).intValue());
        //单号
        driverPlan.setDriverNo("DP"+System.currentTimeMillis());
        driverPlan.setCreateDate(new Date());
        driverPlan.setLastModifyDate(new Date());
        driverPlan.setIsDel(0);
        driverPlanEntityMapper.insert(driverPlan);
        return APIResponse.success();
    }

    //开始行程，返回起点终点的经纬度（导航）
    @Override
    public APIResponse beginPlan(String driverNo) {
        logger.info("finishPlan--->driverNo:{}",driverNo);
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
        driverOrderEntityMapper.updateByPrimaryKeySelective(driverOrder);

        //TODO 需测试有实际订单但是没有乘客订单的情况
        //查询乘客订单
        List<PassengerOrderEntity> passengerOrderList = passengerOrderEntityMapper.selectByDriverOrderNo(driverOrder.getOrderNo());
        passengerOrderList.forEach(
                passengerOrderEntity -> {
                    //更新状态为行驶中，实际出发时间
                    passengerOrderEntity.setStatus(PassengerOrderStatus.TRAVELING.getStatusCode());
                    passengerOrderEntity.setActualStartTime(new Date());
                    passengerOrderEntityMapper.updateByPrimaryKeySelective(passengerOrderEntity);
                }
        );

        return APIResponse.success(driverPlan);
    }

    //判断行程单是否有订单
    @Override
    public APIResponse checkPlanOrder(String driverNo) {
        //查询司机订单
        DriverOrderEntity driverOrder = driverOrderEntityMapper.selectByDriverNo(driverNo);
        if(driverOrder == null){
            return APIResponse.fail("没有订单，取消不扣信用分。");
        }
        return APIResponse.success("已有订单，取消会扣信用分哦~");
    }

    //取消行程单
    @Override
    public APIResponse cancelPlan(String driverNo) {
        logger.info("finishPlan--->driverNo:{}",driverNo);
        //查询行程单
        DriverPlanEntity driverPlan = driverPlanEntityMapper.selectByNo(driverNo);

        List<String> passengerUidList = new ArrayList<>();

        //只有拼车中可以取消
        if(!DriverPlanStatus.WAITING.getCode().equals(driverPlan.getStatus())){
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
        if(!DriverOrderStatus.WAIT_DEPART.getCode().equals(driverOrder.getStatus()) && !DriverOrderStatus.READY.getCode().equals(driverOrder.getStatus())){
            return APIResponse.fail("现在不可以取消哦~");
        }

        //更新司机订单状态为取消
        driverOrder.setStatus(DriverOrderStatus.CANCEL.getCode());
        driverOrderEntityMapper.updateByPrimaryKeySelective(driverOrder);
        //查询乘客端订单
        List<PassengerOrderEntity> passengerOrderList = passengerOrderEntityMapper.selectByDriverOrderNo(driverOrder.getOrderNo());
        passengerOrderList.forEach(passengerOrderEntity -> {
            //更新乘客订单状态为取消
            passengerOrderEntity.setStatus(PassengerOrderStatus.CANCEL.getStatusCode());
            passengerOrderEntityMapper.updateByPrimaryKeySelective(passengerOrderEntity);
            passengerUidList.add(passengerOrderEntity.getPassengerUid());
        });

        //扣除司机
        userService.deductUserCreditScore(driverPlan.getDriverUid());
        //扣除乘客和司机的亲密度---如果有乘客
        if(passengerUidList.size() > 0){
            userRelationService.deductDriverFriendshipScore(driverPlan.getDriverUid(),passengerUidList);
        }

        return APIResponse.success("取消成功！");
    }

    //确认结束，完成行程
    @Override
    public APIResponse finishPlan(DriverPlanRequest driverPlanReq) {

        logger.info("finishPlan--->driverNo:{}",driverPlanReq.getDriverNo());
        //查询行程单
        DriverPlanEntity driverPlan = driverPlanEntityMapper.selectByNo(driverPlanReq.getDriverNo());

        //判断当前距离与终点距离
        Point2D currentPoint = new Point2D.Double(NumberUtils.toDouble(driverPlanReq.getCurrentXpoint()),NumberUtils.toDouble(driverPlanReq.getCurrentYpoint()));
        Point2D endPoint = new Point2D.Double(NumberUtils.toDouble(driverPlan.getEndXpoint()),NumberUtils.toDouble(driverPlan.getEndYpoint()));
        System.out.println(Tools.getDistance(currentPoint,endPoint));
        if(Tools.getDistance(currentPoint,endPoint) >= 2000){
            return APIResponse.fail("距离终点还挺远呢，不可以结束哦`");
        }

        //所有用户uid
        List<String> uidList = new ArrayList<>();

        //更新行程单
        driverPlan.setStatus(DriverPlanStatus.FINISH.getCode());
        driverPlanEntityMapper.updateByPrimaryKey(driverPlan);
        uidList.add(driverPlan.getDriverUid());

        //查询司机订单
        DriverOrderEntity driverOrder = driverOrderEntityMapper.selectByDriverNo(driverPlan.getDriverNo());
        //判断是否有实际订单
        if(driverOrder == null){
            return APIResponse.success();
        }
        driverOrder.setStatus(DriverOrderStatus.FINISH.getCode());
        driverOrder.setActualEndTime(new Date());
        driverOrderEntityMapper.updateByPrimaryKeySelective(driverOrder);
        //查询乘客端订单
        List<PassengerOrderEntity> passengerOrderList = passengerOrderEntityMapper.selectByDriverOrderNo(driverOrder.getOrderNo());
        passengerOrderList.forEach(passengerOrderEntity -> {
            //更新乘客订单状态为取消
            passengerOrderEntity.setStatus(PassengerOrderStatus.COMPLETE.getStatusCode());
            passengerOrderEntity.setActualEndTime(new Date());
            passengerOrderEntityMapper.updateByPrimaryKeySelective(passengerOrderEntity);
            uidList.add(passengerOrderEntity.getPassengerUid());
        });

        //增加司机和乘客的信用分
        userService.addUserCreditScore(uidList);
        //增加亲密度---如果有乘客
        if(uidList.size() > 1){
            userRelationService.addUserFriendshipScore(uidList);
        }

        return APIResponse.success();
    }

    @Override
    public DriverPlanEntity findDriverPlanById(Integer id) {
        return driverPlanEntityMapper.selectByPrimaryKey(id);
    }

    public Integer sumMoney(String uid){
        DriverPlanEntity driverPlanEntity= driverPlanEntityMapper.selectByUidAndStatus(uid,3);
        if(driverPlanEntity==null){ return 0;}
        DriverOrderEntity orderEntity = driverOrderEntityMapper.selectByDriverNo(driverPlanEntity.getDriverNo());
        if(orderEntity==null){ return 0;}
        return driverOrderEntityMapper.selectTotalAmountByDriverNo(driverPlanEntity.getDriverNo());
    }

    @Override
    public DriverPlanResponse findDriverPlanResponseById(Integer id) {
        return driverPlanEntityMapper.selectResponseByPrimaryKey(id);
    }

}
