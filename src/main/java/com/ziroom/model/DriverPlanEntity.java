package com.ziroom.model;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class DriverPlanEntity extends BaseEntity {

    private Integer id;

    private Date planStartTime;

    private Integer carCapacity;

    private String remark;

    private Double planAmount;

    private Integer status;

    private String driverUid;

    private String driverNo;

    private String startXpoint;

    private String startYpoint;

    private String endXpoint;

    private String endYpoint;

    private Integer accountingRules;

    private String startName;

    private String endName;

}