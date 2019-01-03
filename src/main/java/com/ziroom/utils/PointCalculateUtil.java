package com.ziroom.utils;

import java.math.BigDecimal;

/**
 * @Date:2019/1/3 11:05
 * @Author: liuzh
 * @Description: 为了计算地图之间的距离的工具类(需要保留6位小数)
 */
public class PointCalculateUtil {

    /**
     * BigDecimal 保留两位小数
     * @param bigDecimal
     * @return
     */
    private static BigDecimal keep6Point(BigDecimal bigDecimal) {
        return bigDecimal.setScale(6, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 两个数相乘
     * @param num1
     * @param num2
     * @return
     */
    public static String num1MultiplyNum2(String num1,String num2) {
        BigDecimal bigDecimal1 = new BigDecimal(num1);
        BigDecimal bigDecimal2 = new BigDecimal(num2);
        BigDecimal multiply = bigDecimal1.multiply(bigDecimal2);
        return keep6Point(multiply).toString();
    }

    /**
     * @author liuzh
     * @description:开方操作,传进来一个字符串的数字
     * @date 2019/1/3 14:58
     * @param
     * @return
     */
    public static String square(String num) {
        return String.format("%.6f",Math.sqrt(Double.valueOf(num))) ;
    }


    /***
     * 两个数相减（num1-num2）
     * @param num1
     * @param num2
     * @return
     */
    public static String num1SubtractNum2(String num1,String num2) {
        BigDecimal bigDecimal1 = new BigDecimal(num1);
        BigDecimal bigDecimal2 = new BigDecimal(num2);
        BigDecimal multiply = bigDecimal1.subtract(bigDecimal2);
        return keep6Point(multiply).toString();
    }

    /**
     * 两个数相加
     * @param num1
     * @param num2
     * @return
     */
    public static String num1PlusNum2(String num1,String num2) {
        BigDecimal bigDecimal1 = new BigDecimal(num1);
        BigDecimal bigDecimal2 = new BigDecimal(num2);
        BigDecimal multiply = bigDecimal1.add(bigDecimal2);
        return keep6Point(multiply).toString();
    }

}
