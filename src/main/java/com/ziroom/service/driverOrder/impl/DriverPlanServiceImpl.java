package com.ziroom.service.driverOrder.impl;

import com.ziroom.constant.DriverPlanStatus;
import com.ziroom.dao.DriverPlanEntityMapper;
import com.ziroom.model.DriverPlanEntity;
import com.ziroom.service.driverOrder.DriverPlanService;
import com.ziroom.utils.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2019/1/2.
 */
@Service
public class DriverPlanServiceImpl implements DriverPlanService {

    private static Logger logger = LoggerFactory.getLogger(DriverPlanServiceImpl.class);

    @Autowired
    private DriverPlanEntityMapper driverPlanEntityMapper;

    //查询历史记录
    @Override
    public APIResponse getHistoryPlan(String driverUid,Integer status) {
        List<DriverPlanEntity> list = driverPlanEntityMapper.selectByUid(driverUid,status);
        for (DriverPlanEntity driverPlanEntity : list) {

        }
        return APIResponse.success(list);
    }

    //创建拼车单
    @Override
    public APIResponse createDriverPlan(DriverPlanEntity driverPlan) {
        //状态
        driverPlan.setStatus(DriverPlanStatus.WAITING.getCode());
        driverPlan.setCreateTime(new Date());
        driverPlan.setUpdateTime(new Date());
        driverPlan.setIsDel(0);
        driverPlanEntityMapper.insert(driverPlan);
        return APIResponse.success();
    }

    //开始行程，返回起点终点的经纬度（导航）
    @Override
    public APIResponse beginPlan(DriverPlanEntity driverPlan) {
        //修改行程单状态
        driverPlan.setStatus(DriverPlanStatus.DRIVING.getCode());
        driverPlanEntityMapper.updateByPrimaryKey(driverPlan);
        return APIResponse.success(driverPlan);
    }

    //取消行程单
    @Override
    public APIResponse cancelPlan(DriverPlanEntity driverPlan) {
        //1.行程单的状态

        //2.订单的状态

        //3.
        return APIResponse.success();
    }

    //确认结束，完成行程
    @Override
    public APIResponse finishPlan(DriverPlanEntity driverPlan) {
        return APIResponse.success();
    }

    @Override
    public DriverPlanEntity findDriverPlanById(Integer id) {
        return driverPlanEntityMapper.selectByPrimaryKey(id);
    }
}
