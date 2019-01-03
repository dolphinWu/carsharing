package com.ziroom.dto.response;

import com.ziroom.model.DriverPlanEntity;

/**
 * Created by codey on 2019/1/3.
 */
public class DriverPlanResponse extends DriverPlanEntity {

    private Integer passengerCount;//当前乘车人数

    public Integer getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(Integer passengerCount) {
        this.passengerCount = passengerCount;
    }
}
