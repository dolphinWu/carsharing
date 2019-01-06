package com.ziroom.dto.response;

import com.ziroom.model.DriverPlanEntity;

/**
 * Created by codey on 2019/1/3.
 */
public class DriverPlanResponse extends DriverPlanEntity {

    private Integer passengerCount;//当前乘车人数

    private String driverPhoneNumber;

    private String driverName;

    private Double currentMoney;

    private Integer currentPeopleCount;

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

    public Double getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(Double currentMoney) {
        this.currentMoney = currentMoney;
    }

    public Integer getCurrentPeopleCount() {
        return currentPeopleCount;
    }

    public void setCurrentPeopleCount(Integer currentPeopleCount) {
        this.currentPeopleCount = currentPeopleCount;
    }
}
