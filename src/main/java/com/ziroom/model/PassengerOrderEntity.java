package com.ziroom.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class PassengerOrderEntity extends BaseEntity {
    private Integer id;

    private String passengerNo;

    private String driverOrderNo;

    private Integer passengerCount;

    private String startName;

    private String endName;

    private Double actualAmount;

    private Integer status;

    private Date actualStartTime;

    private Date actualEndTime;

    private String passengerUid;

    private String startXpoint;

    private String startYpoint;

    private String endXpoint;

    private String endYpoint;

    private String remark;

}