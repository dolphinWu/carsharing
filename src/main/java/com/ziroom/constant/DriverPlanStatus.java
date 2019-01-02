package com.ziroom.constant;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuhy1 on 2018/5/18.
 *
 *
 */
public enum DriverPlanStatus {

    WAITING(0,"拼车中"),

    READY(1,"满员待出发"),

    DRIVING(2,"行驶中"),

    FINISH(3,"完成"),

    CANCEL(4,"取消")
    ;
    private Integer code;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {

        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    DriverPlanStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static final Map<Integer,String> AFASTATUS = new HashMap<>();

    static {
        for (AfaStatus s : EnumSet.allOf(AfaStatus.class)) {
            AFASTATUS.put(s.getCode(),s.getName());
        }
    }
}
