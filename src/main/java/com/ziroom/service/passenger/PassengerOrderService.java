package com.ziroom.service.passenger;

import com.ziroom.dto.request.PassengerRequest;
import com.ziroom.model.DriverPlanEntity;

import java.util.List;

public interface PassengerOrderService {
    List<DriverPlanEntity> getAllNearByDriverPlan(PassengerRequest passengerRequest);
}
