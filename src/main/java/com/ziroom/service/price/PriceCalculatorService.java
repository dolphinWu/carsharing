package com.ziroom.service.price;

import com.ziroom.dto.request.PPointDistance;
import com.ziroom.dto.response.NameAndCodeModel;

import java.util.List;

/**
 * @Date:2019/1/4 17:39
 * @Author: liuzh
 * @Description:
 */
public interface PriceCalculatorService {
    List<NameAndCodeModel> priceList(PPointDistance pPointDistance);
}
