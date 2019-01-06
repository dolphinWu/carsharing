package com.ziroom.controller;


import com.github.pagehelper.PageInfo;
import com.ziroom.constant.BaseConst;
import com.ziroom.dto.request.PassengerRequest;
import com.ziroom.dto.response.DriverPlanResponse;
import com.ziroom.dto.response.PassengerIndexResponse;
import com.ziroom.model.DriverOrderEntity;
import com.ziroom.model.DriverPlanEntity;
import com.ziroom.model.PassengerOrderEntity;
import com.ziroom.model.UserEntity;
import com.ziroom.service.driverOrder.DriverOrderService;
import com.ziroom.service.driverOrder.DriverPlanService;
import com.ziroom.service.passenger.PassengerOrderService;
import com.ziroom.service.user.UserRelationService;
import com.ziroom.service.user.UserService;
import com.ziroom.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.stream.Collectors;

@Api("乘客端")
@RestController("PassengerController")
@RequestMapping(value = "/api/passenger")
@Slf4j
public class PassengerController extends BaseController {

    @Autowired
    private PassengerOrderService passengerOrderService;

    @Autowired
    private UserService userService;

    @Autowired
    private DriverOrderService driverOrderService;

    @Autowired
    private DriverPlanService driverPlanService;

    @Autowired
    private UserRelationService userRelationService;

    @PostMapping(value = "/index", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "乘客首页信息", notes = "乘客登录之后获取相关所需信息")
    public APIResponse index(PassengerRequest passengerRequest) {
        String uid = passengerRequest.getUid();
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
            Double startToHomeDistance = Tools.getDistance(homeAddress, startPoint);
            Double endToHomeDistance = Tools.getDistance(homeAddress, endPoint);
            //是否在所选地点范围
            boolean pointRange;
            //上班
            if (BaseConst.UseType.ON_DUTY.equals(passengerRequest.getUseType())) {
                pointRange = startToHomeDistance <= passengerRequest.getRadius();
                if (startToHomeDistance > endToHomeDistance) {
                    return false;
                }
            }
            //下班
            else {
                pointRange = endToHomeDistance <= passengerRequest.getRadius();
                if (endToHomeDistance > startToHomeDistance) {
                    return false;
                }
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

    @PostMapping(value = "/viewDriverPlan", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "查看行程单详情")
    public APIResponse viewDriverPlan(@RequestParam("id") int id, @RequestParam("longitude") String longitude,
                                      @RequestParam("latitude") String latitude, @RequestParam("uid") String uid) {
        DriverPlanResponse driverPlanResponse = driverPlanService.findDriverPlanResponseById(id);
        if (driverPlanResponse == null) {
            return APIResponse.fail("行程单不存在，请刷新重试！");
        }

        //乘客目的地点坐标
        Point2D currentPoint = new Point2D.Double(NumberUtils.toDouble(longitude), NumberUtils.toDouble(latitude));
        //获取当前行程单对应的订单
        DriverOrderEntity driverOrderEntity = driverOrderService.findDriverOrderAllInfo(driverPlanResponse.getDriverNo());

        //当前乘客需要付钱数
        String price = PriceCalculateUtil.calculateCurrentUserPrice(driverOrderEntity, driverPlanResponse, currentPoint);
        driverPlanResponse.setCurrentMoney(NumberUtils.toDouble(price));
        if (driverOrderEntity != null) {
            List<PassengerOrderEntity> passengerOrderEntityList = driverOrderEntity.getPassengerOrderEntityList();
            if (CollectionUtils.isNotEmpty(passengerOrderEntityList)) {
                //设置行程单已有人数
                driverPlanResponse.setPassengerCount(passengerOrderEntityList.size() - 1);
            }
        }
        //司机uid
        String driverUid = driverPlanResponse.getDriverUid();
        //司机信用分
        driverPlanResponse.setCreditScore(userService.getUserInfoByUId(driverUid).getCreditScore());
        //亲密度
        driverPlanResponse.setFriendshipScore(userRelationService.selectFriendshipScore(driverUid, uid));

        return APIResponse.success(driverPlanResponse);
    }

    @PostMapping(value = "/joinJourney", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "加入行程单")
    public APIResponse joinJourney(@RequestParam("id") Integer id, @RequestParam("longitude") String longitude,
                                   @RequestParam("latitude") String latitude, @RequestParam(value = "name", required = false) String name,
                                   @RequestParam("uid") String uid) {
        DriverPlanEntity driverPlanEntity = driverPlanService.findDriverPlanById(id);
        if (driverPlanEntity == null) {
            return APIResponse.fail("行程单已取消！");
        }

        //乘客目的地点
        Point2D endPoint = new Point2D.Double(NumberUtils.toDouble(longitude), NumberUtils.toDouble(latitude));
        if (StringUtils.isBlank(name)) {
            UserEntity currentUser = UserUtils.getCurrentUser();
            //设值目的地名称
            name = currentUser.getHomeAddressName();
        }
        return passengerOrderService.joinJourney(driverPlanEntity, endPoint, name);
    }

    @PostMapping(value = "/cancelJourney", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "取消行程单")
    public APIResponse cancelJourney(@RequestParam("id") Integer id, @RequestParam("uid") String uid) {
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
