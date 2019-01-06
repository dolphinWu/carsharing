package com.ziroom.dto.response;

import com.ziroom.model.DriverPlanEntity;
import com.ziroom.model.PassengerOrderEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by codey on 2019/1/3.
 */
@Setter
@Getter
public class DriverPlanResponse extends DriverPlanEntity {

    private Integer passengerCount;//当前乘车人数

    private String driverPhoneNumber;

    private String driverName;

    private Double currentMoney;

    private Integer currentPeopleCount;

    private Integer creditScore;//信用分

    private Integer friendshipScore;//亲密度

    private List<PassengerOrderEntity> passengerOrderEntityList;
}
