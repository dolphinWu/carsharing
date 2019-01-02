package com.ziroom.dto.response;

import com.ziroom.model.DriverPlanEntity;

import java.util.List;

public class PassengerIndexResponse {

    private List<DriverPlanEntity> driverPlanEntityList;

    public List<DriverPlanEntity> getDriverPlanEntityList() {
        return driverPlanEntityList;
    }

    public void setDriverPlanEntityList(List<DriverPlanEntity> driverPlanEntityList) {
        this.driverPlanEntityList = driverPlanEntityList;
    }
}
