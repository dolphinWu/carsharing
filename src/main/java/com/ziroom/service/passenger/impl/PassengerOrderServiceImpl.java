package com.ziroom.service.passenger.impl;

import com.ziroom.constant.BaseConst;
import com.ziroom.constant.PassengerOrderStatus;
import com.ziroom.dao.AddressEntityMapper;
import com.ziroom.dao.DriverOrderEntityMapper;
import com.ziroom.dao.PassengerOrderEntityMapper;
import com.ziroom.dao.UserEntityMapper;
import com.ziroom.dto.request.PassengerRequest;
import com.ziroom.model.DriverOrderEntity;
import com.ziroom.model.DriverPlanEntity;
import com.ziroom.model.PassengerOrderEntity;
import com.ziroom.service.passenger.PassengerOrderService;
import com.ziroom.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PassengerOrderServiceImpl implements PassengerOrderService{

    @Autowired
    private PassengerOrderEntityMapper passengerOrderEntityMapper;

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Autowired
    private AddressEntityMapper addressEntityMapper;

    @Autowired
    private DriverOrderEntityMapper driverOrderEntityMapper;

    @Override
    @Transactional
    public synchronized APIResponse joinJourney(DriverPlanEntity driverPlanEntity) {
        String driverNo = driverPlanEntity.getDriverNo();
        DriverOrderEntity driverOrderEntity = driverOrderEntityMapper.selectByDriverNo(driverNo);
        if (driverOrderEntity == null) {
            driverOrderEntity = new DriverOrderEntity();
            driverOrderEntity.setCreateTime(new Date());
            //订单生成初始状态
            driverOrderEntity.setStatus(1);
            driverOrderEntity.setIsDel(BaseConst.TrueOrFalse.FALSE);
            driverOrderEntity.setDriverNo(driverNo);
            driverOrderEntity.setPassengerCount(1);
            driverOrderEntity.setOrderNo(System.currentTimeMillis()+"");
            driverOrderEntityMapper.insertSelective(driverOrderEntity);
        }

        if (driverOrderEntity.getPassengerCount() == driverPlanEntity.getCarCapacity()) {
            return APIResponse.fail("乘客人数已满");
        }

        PassengerOrderEntity passengerOrderEntity = new PassengerOrderEntity();
        passengerOrderEntity.setDriverOrderNo(driverOrderEntity.getOrderNo());
        passengerOrderEntity.setStatus(1);
        passengerOrderEntityMapper.insertSelective(passengerOrderEntity);
        return APIResponse.success("成功加入行程");
    }

    @Override
    @Transactional
    public APIResponse cancelJourney(Integer id) {
        PassengerOrderEntity passengerOrderEntity = new PassengerOrderEntity();
        passengerOrderEntity.setId(id);
        passengerOrderEntity.setStatus(PassengerOrderStatus.CANCEL.getStatusCode());
        int count = passengerOrderEntityMapper.updateByPrimaryKeySelective(passengerOrderEntity);
        if (count > 0) {
            return APIResponse.success("取消成功");
        }
        return APIResponse.fail("取消失败");
    }
}
