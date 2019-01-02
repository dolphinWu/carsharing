package com.ziroom.model;

import java.util.Date;

public class DriverOrderEntity extends BaseEntity {
    private Integer id;

    private String order_no;

    private String remark;

    private Integer status;

    private Date actual_start_time;

    private Date actual_end_time;

    private String driver_no;

    private Integer actual_amount;

    private Integer passenger_count;

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

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no == null ? null : order_no.trim();
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

    public String getDriver_no() {
        return driver_no;
    }

    public void setDriver_no(String driver_no) {
        this.driver_no = driver_no == null ? null : driver_no.trim();
    }

    public Integer getActual_amount() {
        return actual_amount;
    }

    public void setActual_amount(Integer actual_amount) {
        this.actual_amount = actual_amount;
    }

    public Integer getPassenger_count() {
        return passenger_count;
    }

    public void setPassenger_count(Integer passenger_count) {
        this.passenger_count = passenger_count;
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