package com.ziroom.service.passenger.impl;

import com.ziroom.dao.AddressEntityMapper;
import com.ziroom.dao.PassengerOrderEntityMapper;
import com.ziroom.dao.UserEntityMapper;
import com.ziroom.dto.request.PassengerRequest;
import com.ziroom.dto.response.PassengerIndexResponse;
import com.ziroom.model.DriverPlanEntity;
import com.ziroom.service.passenger.PassengerOrderService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<DriverPlanEntity> getAllNearByDriverPlan(PassengerRequest passengerRequest) {
        Map<String,Object> paramMap = new HashMap<>();
        return passengerOrderEntityMapper.getAllNearByDriverPlan(paramMap);
    }
}
