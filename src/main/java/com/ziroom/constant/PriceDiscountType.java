package com.ziroom.constant;


/**
 * @author liuzh
 * @description:
 * @date 2019/1/4 18:18
 * @param
 * @return 
 */


public enum PriceDiscountType {


    DISCOUNT_1("1.00",1,"1一个人全价"),
    DISCOUNT_2("1.20",2,"2个人6折"),
    DISCOUNT_3("1.50",3,"3个人5折"),
    DISCOUNT_4("1.60",4,"4个人4折");

    private String name;
    private int index;
    private String desc;

    PriceDiscountType(String name, int index, String desc) {
        this.name = name;
        this.index = index;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
