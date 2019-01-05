package com.ziroom.utils;

/**
 * @Date:2019/1/4 15:06
 * @Author: liuzh
 * @Description:
 */
public class PriceCalculateUtil {

    //一个人的折扣,即一人付全价
    public static  String discount1="1.00";
    //两个人的折扣,即每个人6折
    public static String discount2="1.20";
    //三个人的折扣,即每个人5折
    public static String discount3="1.50";
    //四个人的折扣,即每个人4折
    public static String discount4="1.60";

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
    public static String calculatePrice(String count,String totalPrice) {
        Integer countInt=Integer.valueOf(count);
        if (countInt < 1 || countInt > 4) {
            throw new RuntimeException("人数错误");
        }
        switch (countInt) {
            case 1:
                return MathUtil.num1DivideNum2(MathUtil.num1DivideNum2(totalPrice,discount1),"1") ;
            case 2:
                return MathUtil.num1DivideNum2(MathUtil.num1DivideNum2(totalPrice,discount2),"2") ;
            case 3:
                return MathUtil.num1DivideNum2(MathUtil.num1DivideNum2(totalPrice,discount3),"3") ;
            case 4:
                return MathUtil.num1DivideNum2(MathUtil.num1DivideNum2(totalPrice,discount4),"4") ;
        }

        throw new RuntimeException("计算价格参数异常");
    }
}
