package com.ziroom.model;

import java.util.Date;

public class PassengerOrderEntity extends BaseEntity {
    private Integer id;

    private String passenger_no;

    private String driver_order_no;

    private Integer passenger_count;

    private String start_name;

    private String end_name;

    private Integer actual_amount;

    private Integer status;

    private Date actual_start_time;

    private Date actual_end_time;

    private String passenger_uid;

    private String start_xpoint;

    private String start_ypoint;

    private String end_xpoint;

    private String end_ypoint;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassenger_no() {
        return passenger_no;
    }

    public void setPassenger_no(String passenger_no) {
        this.passenger_no = passenger_no == null ? null : passenger_no.trim();
    }

    public String getDriver_order_no() {
        return driver_order_no;
    }

    public void setDriver_order_no(String driver_order_no) {
        this.driver_order_no = driver_order_no == null ? null : driver_order_no.trim();
    }

    public Integer getPassenger_count() {
        return passenger_count;
    }

    public void setPassenger_count(Integer passenger_count) {
        this.passenger_count = passenger_count;
    }

    public String getStart_name() {
        return start_name;
    }

    public void setStart_name(String start_name) {
        this.start_name = start_name == null ? null : start_name.trim();
    }

    public String getEnd_name() {
        return end_name;
    }

    public void setEnd_name(String end_name) {
        this.end_name = end_name == null ? null : end_name.trim();
    }

    public Integer getActual_amount() {
        return actual_amount;
    }

    public void setActual_amount(Integer actual_amount) {
        this.actual_amount = actual_amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getActual_start_time() {
        return actual_start_time;
    }

    public void setActual_start_time(Date actual_start_time) {
        this.actual_start_time = actual_start_time;
    }

    public Date getActual_end_time() {
        return actual_end_time;
    }

    public void setActual_end_time(Date actual_end_time) {
        this.actual_end_time = actual_end_time;
    }

    public String getPassenger_uid() {
        return passenger_uid;
    }

    public void setPassenger_uid(String passenger_uid) {
        this.passenger_uid = passenger_uid == null ? null : passenger_uid.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}