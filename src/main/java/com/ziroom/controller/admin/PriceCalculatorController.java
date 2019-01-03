package com.ziroom.controller.admin;

import com.ziroom.dto.PPointDistance;
import com.ziroom.utils.APIResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Date:2019/1/2 21:18
 * @Author: liuzh
 * @Description:
 */

@Controller
@RequestMapping("priceCalculator")
public class PriceCalculatorController {

    /**
     * 获取标准价
     */
    @RequestMapping("averagePrice")
    @ResponseBody
    public APIResponse averagePrice(@RequestBody PPointDistance pPointDistance) {
        String amount = pPointDistance.totalPrice();
        return APIResponse.success(amount);
    }
}
