package com.ziroom.api.admin;

import com.github.pagehelper.PageInfo;
import com.ziroom.dto.request.PPointDistance;
import com.ziroom.dto.response.NameAndCodeModel;
import com.ziroom.service.price.PriceCalculatorService;
import com.ziroom.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Date:2019/1/2 21:18
 * @Author: liuzh
 * @Description:
 */

@RestController
@RequestMapping("/api/priceCalculator")
public class PriceCalculatorController {

    @Autowired
    private PriceCalculatorService priceCalculatorService;

    /**
     * 获取标准价
     */
    @PostMapping("averagePrice")
    public APIResponse averagePrice(PPointDistance pPointDistance) {
        List<NameAndCodeModel> nameAndCodeModels = priceCalculatorService.priceList(pPointDistance);
        return APIResponse.success(new PageInfo(nameAndCodeModels));
    }
}
