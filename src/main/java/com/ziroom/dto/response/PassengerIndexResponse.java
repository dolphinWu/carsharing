package com.ziroom.dto.response;

import com.ziroom.model.DriverPlanEntity;

import java.util.List;

public class PassengerIndexResponse {

    private String longitude;

    private String latitude;

    private List<DriverPlanEntity> driverPlanEntityList;

    public List<DriverPlanEntity> getDriverPlanEntityList() {
        return driverPlanEntityList;
    }

    public void setDriverPlanEntityList(List<DriverPlanEntity> driverPlanEntityList) {
        this.driverPlanEntityList = driverPlanEntityList;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
