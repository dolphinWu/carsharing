package com.ziroom.model;

import java.util.Date;
import java.util.List;

public class DriverOrderEntity extends BaseEntity {
    private Integer id;

    private String orderNo;

    private String remark;

    private Integer status;

    private Date actualStartTime;

    private Date actualEndTime;

    private String driverNo;

    private Integer actualAmount;

    private Integer passengerCount;

    private List<PassengerOrderEntity> passengerOrderEntityList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(Date actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public Date getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(Date actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    public String getDriverNo() {
        return driverNo;
    }

    public void setDriverNo(String driverNo) {
        this.driverNo = driverNo == null ? null : driverNo.trim();
    }

    public Integer getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Integer actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Integer getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(Integer passengerCount) {
        this.passengerCount = passengerCount;
    }

    public List<PassengerOrderEntity> getPassengerOrderEntityList() {
        return passengerOrderEntityList;
    }

    public void setPassengerOrderEntityList(List<PassengerOrderEntity> passengerOrderEntityList) {
        this.passengerOrderEntityList = passengerOrderEntityList;
    }
}