package com.ziroom.api.h5;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/h5")
public class IndexController {

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

}
