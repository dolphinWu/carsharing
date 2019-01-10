package com.ziroom.utils;

import lombok.extern.slf4j.Slf4j;

import java.awt.geom.Point2D;
import java.math.BigDecimal;

/**
 * @Date:2019/1/3 11:05
 * @Author: liuzh
 * @Description: 为了计算地图之间的距离的工具类(需要保留6位小数)
 */
@Slf4j
public class PointCalculateUtil {

    /**
     * BigDecimal 保留两位小数
     *
     * @param bigDecimal
     * @return
     */
    private static BigDecimal keep6Point(BigDecimal bigDecimal) {
        return bigDecimal.setScale(6, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 两个数相乘
     *
     * @param num1
     * @param num2
     * @return
     */
    public static String num1MultiplyNum2(String num1, String num2) {
        BigDecimal bigDecimal1 = new BigDecimal(num1);
        BigDecimal bigDecimal2 = new BigDecimal(num2);
        BigDecimal multiply = bigDecimal1.multiply(bigDecimal2);
        return keep6Point(multiply).toString();
    }

    /**
     * @param
     * @return
     * @author liuzh
     * @description:开方操作,传进来一个字符串的数字
     * @date 2019/1/3 14:58
     */
    public static String square(String num) {
        return String.format("%.6f", Math.sqrt(Double.valueOf(num)));
    }

    /***
     * 两个数相减（num1-num2）
     * @param num1
     * @param num2
     * @return
     */
    public static String num1SubtractNum2(String num1, String num2) {
        BigDecimal bigDecimal1 = new BigDecimal(num1);
        BigDecimal bigDecimal2 = new BigDecimal(num2);
        BigDecimal multiply = bigDecimal1.subtract(bigDecimal2);
        return keep6Point(multiply).toString();
    }

    /**
     * 两个数相加
     *
     * @param num1
     * @param num2
     * @return
     */
    public static String num1PlusNum2(String num1, String num2) {
        BigDecimal bigDecimal1 = new BigDecimal(num1);
        BigDecimal bigDecimal2 = new BigDecimal(num2);
        BigDecimal multiply = bigDecimal1.add(bigDecimal2);
        return keep6Point(multiply).toString();
    }

    /**
     * 计算途经点是否在范围内
     *
     * @param startPoint 起始点
     * @param endPoint   终点
     * @param midPoint   途经点
     * @return
     * @Param range     途径点到起始点和终点之间的距离范围,单位m, 默认2000m
     */
    public static boolean calculateIsInRange(Point2D startPoint, Point2D endPoint, Point2D midPoint, Double range) {
        double startToEndDistance = Tools.getDistance(startPoint, endPoint);
        double startToMidDistance = Tools.getDistance(startPoint, midPoint);
        double midToEndDistance = Tools.getDistance(midPoint, endPoint);
        //途经点跟终点重合
        if (midToEndDistance == 0) {
            return true;
        }

        //途径点跟起点重合
        if (startToMidDistance == 0) {
            return true;
        }

        //如果在一条直线上，并且是途经点在中间
        if (startToMidDistance + midToEndDistance == startToEndDistance) {
            return true;
        }

        if (hasObtuseAngle(startToEndDistance, startToMidDistance, midToEndDistance)) {
            return false;
        }

        double area = calculateTriangleArea(startToEndDistance, startToMidDistance, midToEndDistance);
        if (area == 0) {
            return false;
        }

        if (range == null) {
            range = 1000D;
        }

        double distance = area / startToEndDistance;
        log.info("途径点到起始点和终点之间的距离：{}", distance);
        return distance <= range;
    }

    /**
     * 计算三角形面积
     *
     * @param a
     * @param b
     * @param c
     * @return
     */
    public static double calculateTriangleArea(double a, double b, double c) {
        if (a + b > c && a + c > b && c + b > a) {
            return Math.sqrt((a + b + c) * (a + b - c) * (a + c - b) * (b + c - a)) / 4;
        }

        return 0;
    }

    public static boolean hasObtuseAngle(double a, double b, double c) {
        double bAngle = Math.toDegrees(Math.acos((a * a + c * c - b * b) / (2 * a * c)));
        log.info("三边距离：{},{},{}-bAngle:{}", a, b, c, bAngle);
        if (bAngle > 90) {
            return true;
        }
        double cAngle = Math.toDegrees(Math.acos((b * b + a * a - c * c) / (2 * b * a)));
        log.info("三边距离：{},{},{}-cAngle:{}", a, b, c, cAngle);
        if (cAngle > 90) {
            return true;
        }
        return false;
    }
}
