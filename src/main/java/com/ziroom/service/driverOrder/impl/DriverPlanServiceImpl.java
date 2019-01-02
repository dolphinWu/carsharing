package com.ziroom.service.driverOrder.impl;

import com.ziroom.dao.DriverPlanEntityMapper;
import com.ziroom.model.DriverPlanEntity;
import com.ziroom.service.driverOrder.DriverPlanService;
import com.ziroom.utils.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public APIResponse getHistoryPlan(String driverUid) {
        List<DriverPlanEntity> list = driverPlanEntityMapper.selectByUid(driverUid);

        return APIResponse.success(list);
    }

    //创建拼车单
    @Override
    public APIResponse createDriverPlan(DriverPlanEntity driverPlan) {
        //driverPlan.setStatus();
        driverPlanEntityMapper.insert(driverPlan);
        return APIResponse.success();
    }

    //开始行程
    @Override
    public APIResponse beginPlan(DriverPlanEntity driverPlan) {
        return APIResponse.success();
    }

    @Override
    public APIResponse cancelPlan(DriverPlanEntity driverPlan) {
        return APIResponse.success();
    }

    @Override
    public APIResponse finishPlan(DriverPlanEntity driverPlan) {
        return APIResponse.success();
    }
}
