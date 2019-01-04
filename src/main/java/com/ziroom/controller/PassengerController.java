package com.ziroom.controller;


import com.ziroom.dto.request.PassengerRequest;
import com.ziroom.dto.response.PassengerIndexResponse;
import com.ziroom.model.AddressEntity;
import com.ziroom.model.DriverPlanEntity;
import com.ziroom.model.UserEntity;
import com.ziroom.service.Address.AddressService;
import com.ziroom.service.driverOrder.DriverPlanService;
import com.ziroom.service.passenger.PassengerOrderService;
import com.ziroom.service.user.UserService;
import com.ziroom.utils.APIResponse;
import com.ziroom.utils.Tools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.stream.Collectors;

@Api("乘客端")
@RestController("PassengerController")
@RequestMapping(value = "/api/passenger")
public class PassengerController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PassengerController.class);

    @Autowired
    private PassengerOrderService passengerOrderService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    @Autowired
    private DriverPlanService driverPlanService;

    @PostMapping(value = "/index", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "乘客首页信息", notes = "乘客登录之后获取相关所需信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "uid", value = "用户id", dataType = "int", required = true),
            @ApiImplicitParam(name = "departTime", value = "发车时间", dataType = "Date"),
            @ApiImplicitParam(name = "longitude", value = "经度", dataType = "String"),
            @ApiImplicitParam(name = "latitude", value = "纬度", dataType = "String"),
            @ApiImplicitParam(name = "radius", value = "终点距离半径", defaultValue = "2000", dataType = "double")})
    public APIResponse index(PassengerRequest passengerRequest, @RequestParam("uid") int uid,
                             @RequestParam(value = "radius", required = false, defaultValue = "2000") double radius) {
        UserEntity userEntity = userService.getUserInfoById(uid);
        if (userEntity == null) {
            return APIResponse.fail("当前用户不存在");
        }

        String longitude = passengerRequest.getLongitude();
        String latitude = passengerRequest.getLatitude();
        if (StringUtils.isBlank(longitude) && StringUtils.isBlank(latitude)) {
            Integer homeAddressId = userEntity.getHomeAddressId();
            if (homeAddressId == null) {
                return APIResponse.fail("未维护常用地址");
            }

            AddressEntity addressEntity = addressService.findAddressById(homeAddressId);
            if (addressEntity == null) {
                return APIResponse.fail("家庭地址已失效");
            }

            longitude = addressEntity.getLongitude();
            latitude = addressEntity.getLatitude();
        }

        Point2D homeAddress = new Point2D.Double(NumberUtils.toDouble(longitude), NumberUtils.toDouble(latitude));
        PassengerIndexResponse passengerIndexResponse = new PassengerIndexResponse();

        //查询最近时间的行程单
        List<DriverPlanEntity> driverPlanEntityList = driverPlanService.getAllDriverPlanInManyHours(passengerRequest);
        driverPlanEntityList = driverPlanEntityList.stream().filter(driverPlanEntity -> {
            //过滤距离小于指定值的行程单
            String endXPoint = driverPlanEntity.getEndXpoint();
            String endYPoint = driverPlanEntity.getEndYpoint();
            return Tools.getDistance(homeAddress, new Point2D.Double(NumberUtils.toDouble(endXPoint), NumberUtils.toDouble(endYPoint))) <= radius;
        }).collect(Collectors.toList());

        passengerIndexResponse.setDriverPlanEntityList(driverPlanEntityList);
        passengerIndexResponse.setLatitude(latitude);
        passengerIndexResponse.setLongitude(longitude);
        return APIResponse.success(passengerIndexResponse);
    }

    @PostMapping(value = "/viewDriverPlan/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public APIResponse viewDriverPlan(@PathVariable("id") int id) {
        DriverPlanEntity driverPlanEntity = driverPlanService.findDriverPlanById(id);
        if (driverPlanEntity == null) {
            return APIResponse.fail("行程单不存在，请刷新重试！");
        }
        return APIResponse.success(driverPlanEntity);
    }

    @PostMapping(value = "/joinJourney/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public APIResponse joinJourney(@PathVariable("id") Integer id) {
        DriverPlanEntity driverPlanEntity = driverPlanService.findDriverPlanById(id);
        if (driverPlanEntity == null) {
            return APIResponse.fail("行程单已取消！");
        }

        return passengerOrderService.joinJourney(driverPlanEntity);
    }

    @PostMapping(value = "/cancelJourney/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public APIResponse cancelJourney(@PathVariable("id") Integer id) {
        return passengerOrderService.cancelJourney(id);
    }
}
