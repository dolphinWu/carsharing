package com.ziroom.service.passenger;

import com.ziroom.dto.request.PassengerRequest;
import com.ziroom.model.DriverPlanEntity;
import com.ziroom.utils.APIResponse;

import java.util.List;

public interface PassengerOrderService {
    APIResponse joinJourney(DriverPlanEntity driverPlanEntity);

    APIResponse cancelJourney(Integer id);
}
