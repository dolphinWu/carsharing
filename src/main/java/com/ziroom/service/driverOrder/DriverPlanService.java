package com.ziroom.service.driverOrder;

import com.ziroom.dto.request.DriverPlanRequest;
import com.ziroom.dto.request.PassengerRequest;
import com.ziroom.dto.response.DriverPlanResponse;
import com.ziroom.model.DriverPlanEntity;
import com.ziroom.utils.APIResponse;

import java.util.List;

/**
 * Created by codey on 2019/1/2.
 */
public interface DriverPlanService {

    /**
     * @author codey
     * @description 查询司机历史记录
     * @date 2019/1/2 16:46
     * @param driverUid
     * @return
     */
   APIResponse getHistoryPlan(String driverUid,Integer pageSize,Integer pageNumber);

   /**
    * @author codey
    * @description 司机创建拼车单
    * @date 2019/1/2 17:42
    * @param
    * @return
    */
   APIResponse createDriverPlan(DriverPlanRequest driverPlanReq);

   /**
    * @author codey
    * @description 行程开始
    * @date 2019/1/2 17:44
    * @param 
    * @return 
    */
   APIResponse beginPlan(String driverNo);

   /**
    * @author codey
    * @description 司机取消拼车单
    * @date 2019/1/2 17:46
    * @param
    * @return
    */
   APIResponse cancelPlan(String driverNo);

   /**
    * @author codey
    * @description 行程完成
    * @date 2019/1/2 17:47
    * @param
    * @return
    */
   APIResponse finishPlan(DriverPlanRequest driverPlanReq);

   DriverPlanEntity findDriverPlanById(Integer id);

   /**
    * 获取一定时间内所有的行程单
    * @param passengerRequest
    * @return
    */
   List<DriverPlanEntity> getAllDriverPlanInManyHours(PassengerRequest passengerRequest);

   /**
    * @author codey
    * @description 查看刚刚发布的行程单
    * @date 2019/1/5 14:47
    * @param
    * @return
    */
    APIResponse getShowPlan(String driverUid);

   Integer sumMoney(String uid);

   DriverPlanResponse findDriverPlanResponseById(Integer id);
}
