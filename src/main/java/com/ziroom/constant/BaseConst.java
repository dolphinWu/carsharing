package com.ziroom.constant;

public interface BaseConst {

    /**
     * 用户类型
     */
    interface UserType {
        //司机
        static final Integer DRIVER = 0;
        //乘客
        static final Integer PASSENGER = 1;
    }

    /**
     * 使用类型
     */
    interface UseType {
        //下班
        static final Integer OFF_DUTY  = 0;
        //上班
        static final Integer ON_DUTY = 1;
    }

    /**
     * 是否
     */
    interface TrueOrFalse {
        static final Integer TRUE = 1;
        static final Integer FALSE = 0;
    }
}
