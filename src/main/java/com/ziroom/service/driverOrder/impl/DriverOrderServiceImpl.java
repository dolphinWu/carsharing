package com.ziroom.service.driverOrder.impl;

import com.ziroom.dao.DriverOrderEntityMapper;
import com.ziroom.model.DriverOrderEntity;
import com.ziroom.service.driverOrder.DriverOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class DriverOrderServiceImpl implements DriverOrderService{

    @Autowired
    private DriverOrderEntityMapper driverOrderEntityMapper;

    @Override
    public DriverOrderEntity findDriverOrderAllInfo(String driverNo) {
        Map<String,Object> map = new HashMap<>();
        map.put("driverNo", driverNo);
        return driverOrderEntityMapper.findDriverOrderAllInfo(map);
    }
}
