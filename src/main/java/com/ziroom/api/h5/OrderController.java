package com.ziroom.api.h5;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {

    @RequestMapping("/orderPage")
    public String orderPage() {

        return "order/order";
    }

}
