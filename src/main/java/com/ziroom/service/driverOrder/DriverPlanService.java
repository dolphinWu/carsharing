package com.ziroom.service.driverOrder;

import com.ziroom.model.DriverPlanEntity;
import com.ziroom.utils.APIResponse;

/**
 * Created by lenovo on 2019/1/2.
 */
public interface DriverPlanService {

    /**
     * @author codey
     * @description 查询司机历史记录
     * @date 2019/1/2 16:46
     * @param driverUid
     * @return
     */
   APIResponse getHistoryPlan(String driverUid,Integer status);

   /**
    * @author codey
    * @description 司机创建拼车单
    * @date 2019/1/2 17:42
    * @param
    * @return
    */
   APIResponse createDriverPlan(DriverPlanEntity driverPlan);

   /**
    * @author codey
    * @description 行程开始
    * @date 2019/1/2 17:44
    * @param 
    * @return 
    */
   APIResponse beginPlan(DriverPlanEntity driverPlan);

   /**
    * @author codey
    * @description 司机取消拼车单
    * @date 2019/1/2 17:46
    * @param
    * @return
    */
   APIResponse cancelPlan(DriverPlanEntity driverPlan);

   /**
    * @author codey
    * @description 行程完成
    * @date 2019/1/2 17:47
    * @param
    * @return
    */
   APIResponse finishPlan(DriverPlanEntity driverPlan);

}
