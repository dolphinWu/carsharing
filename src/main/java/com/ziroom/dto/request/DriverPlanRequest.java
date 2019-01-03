package com.ziroom.dto.request;

import com.ziroom.model.DriverPlanEntity;

/**
 * Created by codey on 2019/1/3.
 */
public class DriverPlanRequest extends DriverPlanEntity {

    private String actualAmount;//实际金额

    public String getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(String actualAmount) {
        this.actualAmount = actualAmount;
    }
}
