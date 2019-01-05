package com.ziroom.controller;


import com.github.pagehelper.PageInfo;
import com.ziroom.constant.BaseConst;
import com.ziroom.dto.request.PassengerRequest;
import com.ziroom.dto.response.DriverPlanResponse;
import com.ziroom.dto.response.PassengerIndexResponse;
import com.ziroom.model.AddressEntity;
import com.ziroom.model.DriverPlanEntity;
import com.ziroom.model.UserEntity;
import com.ziroom.service.Address.AddressService;
import com.ziroom.service.driverOrder.DriverPlanService;
import com.ziroom.service.passenger.PassengerOrderService;
import com.ziroom.service.user.UserService;
import com.ziroom.utils.APIResponse;
import com.ziroom.utils.PointCalculateUtil;
import com.ziroom.utils.Tools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.geom.Point2D;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

@Api("乘客端")
@RestController("PassengerController")
@RequestMapping(value = "/api/passenger")
@Slf4j
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
    public APIResponse index(PassengerRequest passengerRequest) {
        Integer uid = passengerRequest.getUid();
        if (uid == null) {
            return APIResponse.fail("uid必传");
        }

        UserEntity userEntity = userService.getUserInfoByUId(uid);
        if (userEntity == null) {
            return APIResponse.fail("当前用户不存在");
        }

        String longitude = passengerRequest.getLongitude();
        String latitude = passengerRequest.getLatitude();
        if (StringUtils.isBlank(longitude) && StringUtils.isBlank(latitude)) {
            longitude = userEntity.getHomeLongitude();
            latitude = userEntity.getHomeLatitude();
        }

        if (StringUtils.isBlank(longitude) && StringUtils.isBlank(latitude)) {
            return APIResponse.success(null);
        }

        Point2D homeAddress = new Point2D.Double(NumberUtils.toDouble(longitude), NumberUtils.toDouble(latitude));
        PassengerIndexResponse passengerIndexResponse = new PassengerIndexResponse();

        //查询最近时间的行程单
        List<DriverPlanEntity> driverPlanEntityList = driverPlanService.getAllDriverPlanInManyHours(passengerRequest);
        driverPlanEntityList = driverPlanEntityList.parallelStream().filter(driverPlanEntity -> {
            //过滤距离小于指定值的行程单
            String endXPoint = driverPlanEntity.getEndXpoint();
            String endYPoint = driverPlanEntity.getEndYpoint();
            String startXPoint = driverPlanEntity.getStartXpoint();
            String startYPoint = driverPlanEntity.getStartYpoint();
            Point2D startPoint = new Point2D.Double(NumberUtils.toDouble(startXPoint, 0), NumberUtils.toDouble(startYPoint, 0));
            Point2D endPoint = new Point2D.Double(NumberUtils.toDouble(endXPoint, 0), NumberUtils.toDouble(endYPoint, 0));

            //是否在所选地点范围
            boolean pointRange;
            if (BaseConst.UseType.ON_DUTY.equals(passengerRequest.getUseType())) {
                pointRange = Tools.getDistance(homeAddress, startPoint) <= passengerRequest.getRadius();
            } else {
                pointRange = Tools.getDistance(homeAddress, endPoint) <= passengerRequest.getRadius();
            }

            //计算是否在路径上的范围
            boolean pathwayInRange = PointCalculateUtil.calculateIsInRange(startPoint, endPoint, homeAddress, null);
            return pointRange || pathwayInRange;
        }).collect(Collectors.toList());

        passengerIndexResponse.setDriverPlanEntityList(driverPlanEntityList);
        passengerIndexResponse.setLatitude(latitude);
        passengerIndexResponse.setLongitude(longitude);
        return APIResponse.success(passengerIndexResponse);
    }

    @PostMapping(value = "/viewDriverPlan/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "查看行程单详情")
    public APIResponse viewDriverPlan(@PathVariable("id") int id) {
        DriverPlanResponse driverPlanResponse = driverPlanService.findDriverPlanResponseById(id);
        if (driverPlanResponse == null) {
            return APIResponse.fail("行程单不存在，请刷新重试！");
        }
        return APIResponse.success(driverPlanResponse);
    }

    @PostMapping(value = "/joinJourney/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "加入行程单")
    public APIResponse joinJourney(@PathVariable("id") Integer id) {
        DriverPlanEntity driverPlanEntity = driverPlanService.findDriverPlanById(id);
        if (driverPlanEntity == null) {
            return APIResponse.fail("行程单已取消！");
        }

        return passengerOrderService.joinJourney(driverPlanEntity);
    }

    @PostMapping(value = "/cancelJourney/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "取消行程单")
    public APIResponse cancelJourney(@PathVariable("id") Integer id) {
        return passengerOrderService.cancelJourney(id);
    }

    @PostMapping(value = "/findPassengerOrderForPage", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "乘客我的订单")
    public APIResponse findPassengerOrderForPage(@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                 @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
                                                 @RequestParam(value = "uid") Integer uid) {
        PageInfo pageInfo = passengerOrderService.findPassengerOrderForPage(uid, pageSize, pageNumber);
        return APIResponse.success(pageInfo);
    }
}
