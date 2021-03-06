package com.ziroom.controller;

import com.ziroom.dto.request.DriverPlanRequest;
import com.ziroom.service.driverOrder.DriverPlanService;
import com.ziroom.utils.APIResponse;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by codey on 2019/1/3.
 */
@Api("司机端")
@RestController
@RequestMapping("/driver")
public class DriverController {

    private static Logger logger = LoggerFactory.getLogger(DriverController.class);

    @Autowired
    private DriverPlanService driverPlanService;

    /**
     * @author codey
     * @description 历史订单、发布后查询
     * @date 2019/1/3 14:32
     * @param
     * @return com.ziroom.utils.APIResponse
     */
    @PostMapping(value = "/historyPlan",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public APIResponse historyPlan(DriverPlanRequest driverPlanRequest){
        String driverUid = driverPlanRequest.getDriverUid();
        return driverPlanService.getHistoryPlan(driverUid,driverPlanRequest.getPageSize(),driverPlanRequest.getPageNumber());
    }

    /**
     * @author codey
     * @description 查看发布的行程单--司机首页
     * @date 2019/1/5 14:46
     * @param
     * @return
     */
    @PostMapping(value = "/showPlan",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public APIResponse showPlan(DriverPlanRequest driverPlanRequest){
        String driverUid = driverPlanRequest.getDriverUid();
        return driverPlanService.getShowPlan(driverUid);
    }

    /**
     * @author codey
     * @description 创建行程单
     * @date 2019/1/3 14:40
     * @param
     * @return
     */
    @PostMapping(value = "/createDriverPlan",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public APIResponse createDriverPlan(DriverPlanRequest driverPlanRequest){
        return driverPlanService.createDriverPlan(driverPlanRequest);
    }

    /**
     * @author codey
     * @description 确认开始行程
     * @date 2019/1/3 14:53
     * @param
     * @return
     */
    @PostMapping(value = "/beginPlan",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public APIResponse beginPlan(String driverNo){
        return driverPlanService.beginPlan(driverNo);
    }

    /**
     * @author codey
     * @description 判断行程单是否有订单
     * @date 2019/1/6 12:01
     * @param
     * @return
     */
    @PostMapping(value = "/checkPlanOrder",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public APIResponse checkPlanOrder(String driverNo){
        return driverPlanService.checkPlanOrder(driverNo);
    }

    /**
     * @author codey
     * @description 取消行程
     * @date 2019/1/3 14:54
     * @param
     * @return
     */
    @PostMapping(value = "/cancelPlan",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public APIResponse cancelPlan(String driverNo){
        return driverPlanService.cancelPlan(driverNo);
    }

    /**
     * @author codey
     * @description 完成行程
     * @date 2019/1/3 14:54
     * @param
     * @return
     */
    @PostMapping(value = "/finishPlan",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public APIResponse finishPlan(DriverPlanRequest driverPlanRequest){
        return driverPlanService.finishPlan(driverPlanRequest);
    }

}
