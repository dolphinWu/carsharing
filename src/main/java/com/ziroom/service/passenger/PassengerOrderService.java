package com.ziroom.service.passenger;

import com.github.pagehelper.PageInfo;
import com.ziroom.model.DriverPlanEntity;
import com.ziroom.utils.APIResponse;

import java.awt.geom.Point2D;

public interface PassengerOrderService {
    APIResponse joinJourney(DriverPlanEntity driverPlanEntity, Point2D endPoint, String name);

    APIResponse cancelJourney(Integer id);

    PageInfo findPassengerOrderForPage(Integer uid, Integer pageSize, Integer pageNumber);
}
