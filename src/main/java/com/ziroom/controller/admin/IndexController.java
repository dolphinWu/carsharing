package com.ziroom.controller.admin;

import com.ziroom.controller.BaseController;
import com.ziroom.service.log.LogService;
import com.ziroom.service.user.UserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Api("后台首页")
@Controller("adminIndexController")
@RequestMapping(value = "/admin")
public class IndexController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private LogService logService;

    @Autowired
    private UserService userService;

}
