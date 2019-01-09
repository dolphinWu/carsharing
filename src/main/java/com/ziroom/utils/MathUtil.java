package com.ziroom.utils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by liuzh on 2017/3/11 15:30.
 */
public class MathUtil {
    private MathUtil() {}

    /**
     * 得到一个数的相反数
     * @param num
     * @return
     */
    public static String oppositeNum(String num) {
        BigDecimal bigDecimal = new BigDecimal(num);
        return keep2Point(BigDecimal.ZERO.subtract(bigDecimal));
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
        return keep2Point(multiply);
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
        return keep2Point(multiply);
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
        return keep2Point(multiply);
    }

    /**
     * 两个数相除（num1/num2)
     */

    public static String num1DivideNum2(String num1,String num2) {
        BigDecimal bigDecimal1 = new BigDecimal(num1);
        BigDecimal bigDecimal2 = new BigDecimal(num2);
        if (equalZero(num2)) {
            throw new RuntimeException("除数不能为0");
        }
        BigDecimal multiply = bigDecimal1.divide(bigDecimal2,2,BigDecimal.ROUND_HALF_UP);
        return keep2Point(multiply);
    }

    public static boolean equalZero(String num) {
        BigDecimal bigDecimal = new BigDecimal(num);
        String bigDecimal1 = keep2Point(bigDecimal);
        if (bigDecimal1.equals("0.00")) {
            return true;
        }
        return  false;
    }


    /**
     * 单位元转化为分
     * @param num
     * @return
     */
    public static Long yuanToFen(String num) {
        BigDecimal bigDecimal1 = new BigDecimal(num);
        BigDecimal bigDecimal2 = bigDecimal1.multiply(new BigDecimal(100));
        return bigDecimal2.setScale(0,BigDecimal.ROUND_HALF_UP).longValue();
    }

    /**
     * 单位分转化为元
     * @param num
     * @return
     */
    public static Double fenToYuanD(String num) {
        return fenToYuanBigD(num).doubleValue();
    }

    public static BigDecimal fenToYuanBigD(String num) {
        BigDecimal bigDecimal1 = new BigDecimal(num);
        return bigDecimal1.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 单位分转化为元
     * @param num
     * @return
     */
    public static String fenToYuanStr(String num) {
        return  fenToYuanBigD(num).toString();
    }

    /**
     * BigDecimal 保留两位小数
     * @param bigDecimal
     * @return
     */
    private static String keep2Point(BigDecimal bigDecimal) {
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * BigDecimal 不保留小数
     * @param bigDecimal
     * @return
     */
    private static BigDecimal keep0Point(BigDecimal bigDecimal) {
        return bigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 获取一个0-num的随机int值
     */
    public static int getRandomInt(int num) {
        Random random = new Random();
        return random.nextInt(num);
    }

    /**
     * 获取一个0-1之间的随机数
     */
    public static Double getRandomDouble() {
        Random random = new Random();
        return random.nextDouble();
    }

    /**
     * 集合内的数据是连续的
     */

    public static boolean isContinues(List<Integer> datas) {
        Collections.sort(datas);
        int dataMin = datas.get(0);
        for (Integer i : datas) {
            if (dataMin != i) {
                return false;
            }
            dataMin++;
        }
        return true;
    }
    
    /**
     * @author liuzh
     * @description:
     * @date 2019/1/5 20:50
     * @param
     * @return 
     */

    public static String formatFloatNumber(double value) {
        if(value != 0.00){
            java.text.DecimalFormat df = new java.text.DecimalFormat("########.00");
            return df.format(value);
        }else{
            return "0.00";
        }

    }
    public static String formatFloatNumber(Double value) {
        if(value != null){
            if(value.doubleValue() != 0.00){
                java.text.DecimalFormat df = new java.text.DecimalFormat("########.00");
                return df.format(value.doubleValue());
            }else{
                return "0.00";
            }
        }
        return "";
    }

}
