package com.ziroom.controller;


import com.ziroom.dto.request.PassengerRequest;
import com.ziroom.dto.response.PassengerIndexResponse;
import com.ziroom.model.AddressEntity;
import com.ziroom.model.DriverPlanEntity;
import com.ziroom.model.UserEntity;
import com.ziroom.service.Address.AddressService;
import com.ziroom.service.passenger.PassengerOrderService;
import com.ziroom.utils.APIResponse;
import com.ziroom.utils.Tools;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Api("乘客端")
@RestController("PassengerController")
@RequestMapping(value = "/passenger")
public class PassengerController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PassengerController.class);

    @Autowired
    private PassengerOrderService passengerOrderService;

    @Autowired
    private AddressService addressService;

    @GetMapping(value = "/index", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public APIResponse<PassengerIndexResponse> index(PassengerRequest passengerRequest,
                                                           @RequestParam(value = "radius", required = false, defaultValue = "2000") double radius) {
        UserEntity userEntity = new UserEntity();
        Integer homeAddressId = userEntity.getHomeAddressId();
        if (homeAddressId == null) {
            return APIResponse.fail("未维护常用地址");
        }

        AddressEntity addressEntity = addressService.findAddressById(homeAddressId);
        if (addressEntity == null) {
            return APIResponse.fail("家庭地址已失效");
        }

        String longitude = addressEntity.getLongitude();
        String latitude = addressEntity.getLatitude();
        Point2D homeAddress = new Point2D.Double(NumberUtils.toDouble(longitude), NumberUtils.toDouble(latitude));
        PassengerIndexResponse passengerIndexResponse = new PassengerIndexResponse();
        List<DriverPlanEntity> driverPlanEntityList = passengerOrderService.getAllNearByDriverPlan(passengerRequest);
        driverPlanEntityList = driverPlanEntityList.stream().filter(driverPlanEntity -> {
            String endXpoint = driverPlanEntity.getEndXpoint();
            String endYpoint = driverPlanEntity.getEndYpoint();
            return Tools.getDistance(homeAddress, new Point2D.Double(NumberUtils.toDouble(endXpoint), NumberUtils.toDouble(endYpoint))) <= radius ;
        }).collect(Collectors.toList());

        passengerIndexResponse.setDriverPlanEntityList(driverPlanEntityList);
        return APIResponse.success(passengerIndexResponse);
    }

}
