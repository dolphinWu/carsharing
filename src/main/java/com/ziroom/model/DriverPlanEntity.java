package com.ziroom.model;

import java.util.Date;

public class DriverPlanEntity extends BaseEntity {
    private Integer id;

    private Date planStartTime;

    private Integer carCapacity;

    private String remark;

    private Integer planAmount;

    private Integer status;

    private String driverUid;

    private String drvierNo;

    private String startXpoint;

    private String startYpoint;

    private String endXpoint;

    private String endYpoint;

    private Integer accountingRules;

    private Date createTime;

    private Date updateTime;

    private String creator;

    private String updator;

    private Integer isDel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    public Integer getCarCapacity() {
        return carCapacity;
    }

    public void setCarCapacity(Integer carCapacity) {
        this.carCapacity = carCapacity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getPlanAmount() {
        return planAmount;
    }

    public void setPlanAmount(Integer planAmount) {
        this.planAmount = planAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDriverUid() {
        return driverUid;
    }

    public void setDriverUid(String driverUid) {
        this.driverUid = driverUid == null ? null : driverUid.trim();
    }

    public String getDrvierNo() {
        return drvierNo;
    }

    public void setDrvierNo(String drvierNo) {
        this.drvierNo = drvierNo == null ? null : drvierNo.trim();
    }

    public String getStartXpoint() {
        return startXpoint;
    }

    public void setStartXpoint(String startXpoint) {
        this.startXpoint = startXpoint == null ? null : startXpoint.trim();
    }

    public String getStartYpoint() {
        return startYpoint;
    }

    public void setStartYpoint(String startYpoint) {
        this.startYpoint = startYpoint == null ? null : startYpoint.trim();
    }

    public String getEndXpoint() {
        return endXpoint;
    }

    public void setEndXpoint(String endXpoint) {
        this.endXpoint = endXpoint == null ? null : endXpoint.trim();
    }

    public String getEndYpoint() {
        return endYpoint;
    }

    public void setEndYpoint(String endYpoint) {
        this.endYpoint = endYpoint == null ? null : endYpoint.trim();
    }

    public Integer getAccountingRules() {
        return accountingRules;
    }

    public void setAccountingRules(Integer accountingRules) {
        this.accountingRules = accountingRules;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}