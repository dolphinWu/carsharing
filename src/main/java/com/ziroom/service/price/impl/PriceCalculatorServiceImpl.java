package com.ziroom.service.price.impl;

import com.ziroom.constant.PriceCalculateType;
import com.ziroom.dto.request.PPointDistance;
import com.ziroom.dto.response.NameAndCodeModel;
import com.ziroom.service.price.PriceCalculatorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * @Date:2019/1/4 17:41
 * @Author: liuzh
 * @Description:
 */
@Service
public class PriceCalculatorServiceImpl implements PriceCalculatorService {

    @Override
    public List<NameAndCodeModel> priceList(PPointDistance pPointDistance) {
        List<NameAndCodeModel> list = new ArrayList<>();
        for (PriceCalculateType s : EnumSet.allOf(PriceCalculateType.class)) {
            NameAndCodeModel nameAndCodeModel = new NameAndCodeModel();
            nameAndCodeModel.setCode(s.getIndex()+"");
            if (s.getIndex() == 2) {
                nameAndCodeModel.setName("0.00");
            } else {
                nameAndCodeModel.setName(pPointDistance.totalPrice());
            }
            list.add(nameAndCodeModel);
        }
        return list;
    }
}
