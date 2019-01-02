package com.ziroom.model;

import java.util.Date;

public class DriverPlanEntity extends BaseEntity {
    private Integer id;

    private Date plan_start_time;

    private Integer car_capacity;

    private String remark;

    private Integer plan_amount;

    private Integer status;

    private String driver_uid;

    private String drvier_no;

    private String start_xpoint;

    private String start_ypoint;

    private String end_xpoint;

    private String end_ypoint;

    private Integer accounting_rules;

    private Date create_time;

    private Date update_time;

    private String creator;

    private String updator;

    private Integer is_del;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getPlan_start_time() {
        return plan_start_time;
    }

    public void setPlan_start_time(Date plan_start_time) {
        this.plan_start_time = plan_start_time;
    }

    public Integer getCar_capacity() {
        return car_capacity;
    }

    public void setCar_capacity(Integer car_capacity) {
        this.car_capacity = car_capacity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getPlan_amount() {
        return plan_amount;
    }

    public void setPlan_amount(Integer plan_amount) {
        this.plan_amount = plan_amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDriver_uid() {
        return driver_uid;
    }

    public void setDriver_uid(String driver_uid) {
        this.driver_uid = driver_uid == null ? null : driver_uid.trim();
    }

    public String getDrvier_no() {
        return drvier_no;
    }

    public void setDrvier_no(String drvier_no) {
        this.drvier_no = drvier_no == null ? null : drvier_no.trim();
    }

    public String getStart_xpoint() {
        return start_xpoint;
    }

    public void setStart_xpoint(String start_xpoint) {
        this.start_xpoint = start_xpoint == null ? null : start_xpoint.trim();
    }

    public String getStart_ypoint() {
        return start_ypoint;
    }

    public void setStart_ypoint(String start_ypoint) {
        this.start_ypoint = start_ypoint == null ? null : start_ypoint.trim();
    }

    public String getEnd_xpoint() {
        return end_xpoint;
    }

    public void setEnd_xpoint(String end_xpoint) {
        this.end_xpoint = end_xpoint == null ? null : end_xpoint.trim();
    }

    public String getEnd_ypoint() {
        return end_ypoint;
    }

    public void setEnd_ypoint(String end_ypoint) {
        this.end_ypoint = end_ypoint == null ? null : end_ypoint.trim();
    }

    public Integer getAccounting_rules() {
        return accounting_rules;
    }

    public void setAccounting_rules(Integer accounting_rules) {
        this.accounting_rules = accounting_rules;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
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

    public Integer getIs_del() {
        return is_del;
    }

    public void setIs_del(Integer is_del) {
        this.is_del = is_del;
    }
}