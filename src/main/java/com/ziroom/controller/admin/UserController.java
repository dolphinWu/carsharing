package com.ziroom.controller.admin;

import com.ziroom.service.user.UserService;
import com.ziroom.utils.APIResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api("用户管理类")
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/Hello")
    @ResponseBody
    public APIResponse<String> hello(){
        return APIResponse.success( "Hello ZiRoomer");
    }
    
}
