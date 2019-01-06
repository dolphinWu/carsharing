package com.ziroom.service.passenger.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ziroom.constant.BaseConst;
import com.ziroom.constant.DriverOrderStatus;
import com.ziroom.constant.PassengerOrderStatus;
import com.ziroom.dao.DriverOrderEntityMapper;
import com.ziroom.dao.DriverPlanEntityMapper;
import com.ziroom.dao.PassengerOrderEntityMapper;
import com.ziroom.dao.UserEntityMapper;
import com.ziroom.dto.response.PassengerOrderResponse;
import com.ziroom.model.DriverOrderEntity;
import com.ziroom.model.DriverPlanEntity;
import com.ziroom.model.PassengerOrderEntity;
import com.ziroom.model.UserEntity;
import com.ziroom.service.passenger.PassengerOrderService;
import com.ziroom.service.user.UserService;
import com.ziroom.utils.APIResponse;
import com.ziroom.utils.PriceCalculateUtil;
import com.ziroom.utils.UserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.geom.Point2D;
import java.util.*;

@Service
public class PassengerOrderServiceImpl implements PassengerOrderService {

    @Autowired
    private PassengerOrderEntityMapper passengerOrderEntityMapper;

    @Autowired
    private DriverOrderEntityMapper driverOrderEntityMapper;

    @Autowired
    private DriverPlanEntityMapper driverPlanEntityMapper;

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Autowired
    private UserService userService;

    /**
     * 加入行程
     *
     * @param driverPlanEntity
     * @param currentPoint
     * @param name
     * @return
     */
    @Override
    @Transactional
    public synchronized APIResponse joinJourney(DriverPlanEntity driverPlanEntity, Point2D currentPoint, String name) {
        UserEntity currentUser = UserUtils.getCurrentUser();
        if (currentUser == null) {
            return APIResponse.fail("用户未登录");
        }

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("uid", currentUser.getUid());
        paramMap.put("status", 1);
        List<PassengerOrderEntity> passengerOrderList = passengerOrderEntityMapper.findList(paramMap);
        if (CollectionUtils.isNotEmpty(passengerOrderList)) {
            return APIResponse.fail("您已加入行程");
        }

        String uname = currentUser.getUname();
        Date now = new Date();
        String driverNo = driverPlanEntity.getDriverNo();
        //查询当前行程单是否已经产生订单
        DriverOrderEntity driverOrderEntity = driverOrderEntityMapper.selectByDriverNo(driverNo);
        if (driverOrderEntity == null) {
            //没有的话就创建订单
            driverOrderEntity = new DriverOrderEntity();
            driverOrderEntity.setCreateDate(now);
            driverOrderEntity.setCreateUser(uname);
            driverOrderEntity.setLastModifyDate(now);
            driverOrderEntity.setLastModifyUser(uname);
            //订单生成初始状态
            driverOrderEntity.setStatus(DriverOrderStatus.WAIT_DEPART.getCode());
            driverOrderEntity.setIsDel(BaseConst.TrueOrFalse.FALSE);
            driverOrderEntity.setDriverNo(driverNo);
            driverOrderEntity.setPassengerCount(1);
            driverOrderEntity.setOrderNo("DO-" + System.currentTimeMillis());
            //如果满员修改状态
            if (driverOrderEntity.getPassengerCount().equals(driverPlanEntity.getCarCapacity())) {
                driverOrderEntity.setStatus(DriverOrderStatus.READY.getCode());
            }
            driverOrderEntityMapper.insertSelective(driverOrderEntity);
        } else {
            Integer passengerCount = driverOrderEntity.getPassengerCount();
            //判断是否满员
            if (DriverOrderStatus.READY.getCode().equals(driverOrderEntity.getStatus())) {
                return APIResponse.fail("乘客人数已满");
            }

            driverOrderEntity.setLastModifyDate(now);
            driverOrderEntity.setLastModifyUser(uname);
            driverOrderEntity.setPassengerCount(passengerCount++);

            if (driverPlanEntity.getCarCapacity().equals(passengerCount)) {
                driverOrderEntity.setStatus(DriverOrderStatus.READY.getCode());
            }

            driverOrderEntityMapper.updateByPrimaryKeySelective(driverOrderEntity);
        }

        PassengerOrderEntity passengerOrderEntity = new PassengerOrderEntity();
        passengerOrderEntity.setDriverOrderNo(driverOrderEntity.getOrderNo());
        passengerOrderEntity.setStatus(PassengerOrderStatus.WAIT_DEPART.getStatusCode());
        passengerOrderEntity.setEndXpoint(currentPoint.getX() + "");
        passengerOrderEntity.setEndYpoint(currentPoint.getY() + "");
        passengerOrderEntity.setEndName(name);
        passengerOrderEntity.setStartName(driverPlanEntity.getStartName());
        passengerOrderEntity.setStartXpoint(driverPlanEntity.getStartXpoint());
        passengerOrderEntity.setStartYpoint(driverPlanEntity.getStartYpoint());
        passengerOrderEntity.setPassengerNo("PN-" + System.currentTimeMillis());
        passengerOrderEntity.setPassengerCount(1);
        passengerOrderEntity.setPassengerUid(currentUser.getUid());
        passengerOrderEntity.setCreateDate(now);
        passengerOrderEntity.setCreateUser(uname);
        passengerOrderEntity.setLastModifyDate(now);
        passengerOrderEntity.setLastModifyUser(uname);
        passengerOrderEntity.setIsDel(BaseConst.TrueOrFalse.FALSE);

        List<PassengerOrderEntity> passengerOrderEntityList = driverOrderEntity.getPassengerOrderEntityList();
        if (passengerOrderEntityList == null) {
            passengerOrderEntityList = new ArrayList<>();
        }

        passengerOrderEntityList.add(passengerOrderEntity);
        List<Point2D> point2DS = new ArrayList<>();
        //乘客的订单跟点的映射
        Map<PassengerOrderEntity, Point2D> map = new HashMap<>();
        passengerOrderEntityList.stream().forEach(orderEntity -> {
            Point2D.Double passengerPoint = new Point2D.Double(NumberUtils.toDouble(orderEntity.getEndXpoint()), NumberUtils.toDouble(orderEntity.getEndYpoint()));
            map.put(orderEntity, passengerPoint);
            point2DS.add(passengerPoint);
        });

        //行程单起始结束点
        Point2D startPoint = new Point2D.Double(NumberUtils.toDouble(driverPlanEntity.getStartXpoint()), NumberUtils.toDouble(driverPlanEntity.getStartYpoint()));
        Point2D endPoint = new Point2D.Double(NumberUtils.toDouble(driverPlanEntity.getEndXpoint()), NumberUtils.toDouble(driverPlanEntity.getEndYpoint()));
        Map<Point2D, String> priceMap = PriceCalculateUtil.calculatePrice(driverPlanEntity.getPlanAmount() + "", point2DS, startPoint, endPoint, driverPlanEntity.getAccountingRules());

        //更新每个人的价钱
        map.keySet().stream().forEach(orderEntity -> {
            Point2D point2D = map.get(orderEntity);
            String price = priceMap.get(point2D);
            if (orderEntity.equals(passengerOrderEntity)) {
                passengerOrderEntity.setActualAmount(Double.valueOf(price));
            } else {
                PassengerOrderEntity updateEntity = new PassengerOrderEntity();
                updateEntity.setId(orderEntity.getId());
                updateEntity.setActualAmount(Double.valueOf(price));
                passengerOrderEntityMapper.updateByPrimaryKeySelective(updateEntity);
            }
        });

        passengerOrderEntityMapper.insertSelective(passengerOrderEntity);
        return APIResponse.success("成功加入行程");
    }

    @Override
    @Transactional
    public APIResponse cancelJourney(Integer id) {
        UserEntity currentUser = UserUtils.getCurrentUser();
        PassengerOrderEntity passengerOrderEntity = passengerOrderEntityMapper.selectByPrimaryKey(id);
        if (passengerOrderEntity == null) {
            return APIResponse.fail("乘客订单不存在");
        }

        PassengerOrderEntity updateOrderEntity = new PassengerOrderEntity();
        updateOrderEntity.setId(id);
        updateOrderEntity.setStatus(PassengerOrderStatus.CANCEL.getStatusCode());
        int count = passengerOrderEntityMapper.updateByPrimaryKeySelective(updateOrderEntity);
        if (count > 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("driverOrderNo", passengerOrderEntity.getDriverOrderNo());
            DriverOrderEntity driverOrderEntity = driverOrderEntityMapper.findDriverOrderAllInfo(map);
            if (driverOrderEntity != null) {
                List<PassengerOrderEntity> passengerOrderEntityList = driverOrderEntity.getPassengerOrderEntityList();
                if (CollectionUtils.isNotEmpty(passengerOrderEntityList)) {
                    DriverPlanEntity driverPlanEntity = driverPlanEntityMapper.selectByNo(driverOrderEntity.getDriverNo());
                    if (driverOrderEntity == null) {
                        return APIResponse.fail("行程单不存在");
                    }

                    //修改每个人金额
                    PriceCalculateUtil.calculateAndSetMoney(driverPlanEntity, passengerOrderEntityList);
                    passengerOrderEntityList.stream().forEach(orderEntity -> {
                        PassengerOrderEntity updateEntity = new PassengerOrderEntity();
                        updateEntity.setId(orderEntity.getId());
                        updateEntity.setActualAmount(orderEntity.getActualAmount());
                        passengerOrderEntityMapper.updateByPrimaryKeySelective(updateEntity);
                    });
                }
            }

            //取消减信用分
            userService.deductUserCreditScore(currentUser.getUid());
            return APIResponse.success("取消成功");
        }
        return APIResponse.fail("取消失败");
    }

    @Override
    public PageInfo findPassengerOrderForPage(Integer uid, Integer pageSize, Integer pageNumber) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("uid", uid);
        PageHelper.startPage(pageNumber, pageSize, true);
        List<PassengerOrderResponse> passengerOrderResponses = new ArrayList<>();
        List<PassengerOrderEntity> passengerOrderEntityList = passengerOrderEntityMapper.findList(paramsMap);
        passengerOrderEntityList.forEach(passengerOrderEntity -> {
            PassengerOrderResponse passengerOrderResponse = new PassengerOrderResponse();
            BeanUtils.copyProperties(passengerOrderEntity, passengerOrderResponse);
            //查询司机订单
            DriverOrderEntity driverOrderEntity = driverOrderEntityMapper.selectByDriverOrderNo(passengerOrderEntity.getDriverOrderNo());
            if (driverOrderEntity != null) {
                DriverPlanEntity driverPlanEntity = driverPlanEntityMapper.selectByNo(driverOrderEntity.getDriverNo());
                //查询司机信息
                UserEntity driver = userEntityMapper.selectByUId(driverPlanEntity.getDriverUid());
                if (driver != null) {
                    passengerOrderResponse.setUname(driver.getUname());
                    passengerOrderResponse.setPhoneNumber(driver.getPhoneNumber());
                    passengerOrderResponses.add(passengerOrderResponse);
                }
            }
        });
        return new PageInfo<>(passengerOrderResponses);
    }
}
