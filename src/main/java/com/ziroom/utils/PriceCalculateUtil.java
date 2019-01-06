package com.ziroom.utils;

import com.ziroom.constant.PriceCalculateType;
import com.ziroom.constant.PriceDiscountType;
import com.ziroom.model.DriverOrderEntity;
import com.ziroom.model.DriverPlanEntity;
import com.ziroom.model.PassengerOrderEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.awt.geom.Point2D;
import java.util.*;

/**
 * @Date:2019/1/4 15:06
 * @Author: liuzh
 * @Description:
 */
@Slf4j
public class PriceCalculateUtil {

    //一个人的折扣,即一人付全价
    public static String discount1 = "1.00";
    //两个人的折扣,即每个人6折
    public static String discount2 = "1.20";
    //三个人的折扣,即每个人5折
    public static String discount3 = "1.40";
    //四个人的折扣,即每个人4折
    public static String discount4 = "1.60";

    //两个人的折扣
    /**
     * @author liuzh
     * @description:计算乘客端的费用
     * 一个人全价
     * 两个人6折
     * 三个人5折
     * 4个人4折
     * @date 2019/1/4 15:07
     * @param
     * @return
     */
    public static String calculatePrice(String count,String totalPrice,String distanceRate) {
        Integer countInt=Integer.valueOf(count);
        if (countInt < 1 || countInt > 4) {
            throw new RuntimeException("人数错误");
        }
        for (PriceDiscountType priceDiscountType : EnumSet.allOf(PriceDiscountType.class)) {
            if (priceDiscountType.getIndex() != countInt) {
                continue;
            }
            String amount = MathUtil.num1DivideNum2(MathUtil.num1DivideNum2(totalPrice, priceDiscountType.getName()), priceDiscountType.getIndex() + "");
            return  MathUtil.num1DivideNum2(amount,distanceRate);
        }

        throw new RuntimeException("计算价格参数异常");
    }

    /**
     * @author liuzh
     * @description:
     * @date 2019/1/5 17:58
     * @param point2DS:当前所有的乘客的坐标点,
     * @param driverPointS:行程单开始的坐标,
     * @param driverPointE:行程单结束的坐标
     * @return 
     */
    public static Map<Point2D,String> calculatePrice(String totalPrice, List<Point2D> point2DS,
                                                     Point2D driverPointS,Point2D driverPointE,int accountingRules) {
        if (point2DS == null || point2DS.isEmpty()) {
            throw new RuntimeException("乘客端的列表点不能为空");
        }
        if (point2DS.size() > 4) {
            throw new RuntimeException("乘客端的列表筛选存在问题");
        }
        String totalDistance = "0.00";
        for (Point2D point2D : point2DS) {
            totalDistance = MathUtil.num1PlusNum2(totalDistance,MathUtil.formatFloatNumber(Tools.getDistance(point2D,driverPointS)));
        }

        //所有人里程数相加是一倍里程数的倍数
        String allPriceRate =  MathUtil.num1DivideNum2(totalDistance,MathUtil.formatFloatNumber(Tools.getDistance(driverPointS,driverPointE)));
        //所有付钱的总数
        String allPrice = calculateAllPrice(totalPrice,allPriceRate);
        //再计算每个人乘客的付钱(按照乘客的比例系数)
        Map<Point2D,String> rateMap = new LinkedHashMap<>();
        for (Point2D point2D : point2DS) {
            if (accountingRules == PriceCalculateType.AVERAGE_PRICE.getIndex()) {
                rateMap.put(point2D, totalPrice);
            } else {
                String currPointRate = MathUtil.num1DivideNum2(MathUtil.formatFloatNumber(Tools.getDistance(point2D, driverPointS)), totalDistance);
                rateMap.put(point2D,MathUtil.num1MultiplyNum2(currPointRate,allPrice));
            }
        }
        return rateMap;
    }

    private static String calculateAllPrice(String totalPrice, String allPriceRate) {
        String stepRate = MathUtil.num1SubtractNum2(allPriceRate, "1.00");
        //每多出1倍的里程数,就会多0.2倍的收益
        String stepFeeAll = MathUtil.num1MultiplyNum2(totalPrice, "0.20");
        //不足一倍的里程数
        if (stepFeeAll.contains("-")) {
            return MathUtil.num1DivideNum2(totalPrice, allPriceRate);
        }
        String stepFeePart = MathUtil.num1MultiplyNum2(stepFeeAll, stepRate);
        return MathUtil.num1PlusNum2(totalPrice,stepFeePart);
    }


    /**
     * 计算当前用户价格
     *
     * @param driverOrderEntity
     * @param driverPlanEntity
     * @param currentUserPoint
     * @return
     */
    public static String calculateCurrentUserPrice(DriverOrderEntity driverOrderEntity, DriverPlanEntity driverPlanEntity, Point2D currentUserPoint) {
        List<Point2D> point2DS = new ArrayList<>();
        if (driverOrderEntity != null) {
            //获取当前订单级联的乘客订单
            List<PassengerOrderEntity> passengerOrderEntityList = driverOrderEntity.getPassengerOrderEntityList();
            point2DS.add(currentUserPoint);
            if (CollectionUtils.isNotEmpty(passengerOrderEntityList)) {
                //添加所有乘客的目的地点坐标
                passengerOrderEntityList.stream().forEach(passengerOrderEntity -> {
                    point2DS.add(new Point2D.Double(NumberUtils.toDouble(passengerOrderEntity.getEndXpoint()), NumberUtils.toDouble(passengerOrderEntity.getEndYpoint())));
                });
            }
        }

        //司机起始终止坐标
        Point2D startPoint = new Point2D.Double(NumberUtils.toDouble(driverPlanEntity.getStartXpoint()), NumberUtils.toDouble(driverPlanEntity.getStartYpoint()));
        Point2D endPoint = new Point2D.Double(NumberUtils.toDouble(driverPlanEntity.getEndXpoint()), NumberUtils.toDouble(driverPlanEntity.getEndYpoint()));
        //计算每个人的价格
        Map<Point2D, String> point2DStringMap = PriceCalculateUtil.calculatePrice(driverPlanEntity.getPlanAmount() + "", point2DS, startPoint, endPoint);
        //当前乘客需要付钱数
        String price = point2DStringMap.get(currentUserPoint);
        return price;
    }
}
