package com.ziroom.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class DriverOrderEntity extends BaseEntity {
    private Integer id;

    private String orderNo;

    private String remark;

    private Integer status;

    private Date actualStartTime;

    private Date actualEndTime;

    private String driverNo;

    private Double actualAmount;

    private Integer passengerCount;

    private List<PassengerOrderEntity> passengerOrderEntityList;

}