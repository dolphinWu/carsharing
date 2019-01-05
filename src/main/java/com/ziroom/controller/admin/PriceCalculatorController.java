package com.ziroom.controller.admin;

import com.ziroom.dto.request.PPointDistance;
import com.ziroom.dto.response.NameAndCodeModel;
import com.ziroom.service.price.PriceCalculatorService;
import com.ziroom.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Date:2019/1/2 21:18
 * @Author: liuzh
 * @Description:
 */

@Controller
@RequestMapping("priceCalculator")
public class PriceCalculatorController {

    @Autowired
    private PriceCalculatorService priceCalculatorService;

    /**
     * 获取标准价
     */
    @RequestMapping("averagePrice")
    @ResponseBody
    public APIResponse averagePrice(@RequestBody PPointDistance pPointDistance) {
        List<NameAndCodeModel> nameAndCodeModels = priceCalculatorService.priceList(pPointDistance);
        return APIResponse.success(nameAndCodeModels);
    }
}
