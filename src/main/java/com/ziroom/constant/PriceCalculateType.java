package com.ziroom.constant;

/**
 * @Date:2019/1/4 18:15
 * @Author: liuzh
 * @Description:
 */
public enum PriceCalculateType {
    AVERAGE_PRICE("均摊",1),
    ONE_PRICE("一口价",2),
    WORK_OVERTIME("加班",3);

    private String name;
    private int index;

    PriceCalculateType(String name, int index) {
        this.name = name;
        this.index = index;
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

}
