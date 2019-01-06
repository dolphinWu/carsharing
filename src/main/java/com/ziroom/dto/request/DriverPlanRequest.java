package com.ziroom.dto.request;

import com.ziroom.model.DriverPlanEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by codey on 2019/1/3.
 */
@Setter
@Getter
@ToString
public class DriverPlanRequest extends DriverPlanEntity {

    private String actualAmount;//实际金额

    private String currentXpoint;

    private String currentYpoint;

    private Integer pageSize;

    private Integer pageNumber;

}
