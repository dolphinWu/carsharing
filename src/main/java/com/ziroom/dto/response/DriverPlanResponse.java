package com.ziroom.dto.response;

import com.ziroom.model.DriverPlanEntity;

/**
 * Created by codey on 2019/1/3.
 */
public class DriverPlanResponse extends DriverPlanEntity {

    private Integer passengerCount;//当前乘车人数

    private String driverPhoneNumber;

    private String driverName;

    public Integer getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(Integer passengerCount) {
        this.passengerCount = passengerCount;
    }

    public String getDriverPhoneNumber() {
        return driverPhoneNumber;
    }

    public void setDriverPhoneNumber(String driverPhoneNumber) {
        this.driverPhoneNumber = driverPhoneNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}
