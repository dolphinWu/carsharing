package com.ziroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author wudaoping
 * @Date 19-1-23 下午8:55
 */
@Controller
@RequestMapping("/im")
public class ImController {


    @RequestMapping("/index")
    public String index() {
        return "im";
    }

}
