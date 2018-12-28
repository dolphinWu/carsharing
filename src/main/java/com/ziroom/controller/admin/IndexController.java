package com.ziroom.controller.admin;

import com.ziroom.constant.LogActions;
import com.ziroom.constant.WebConst;
import com.ziroom.controller.BaseController;
import com.ziroom.exception.BusinessException;
import com.ziroom.model.UserEntity;
import com.ziroom.service.log.LogService;
import com.ziroom.service.user.UserService;
import com.ziroom.utils.APIResponse;
import com.ziroom.utils.TaleUtils;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Api("后台首页")
@Controller("adminIndexController")
@RequestMapping(value = "/admin")
public class IndexController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private LogService logService;

    @Autowired
    private UserService userService;


    /**
     * 修改密码
     */
    @PostMapping(value = "/password")
    @ResponseBody
    public APIResponse upPwd(@RequestParam String oldPassword, @RequestParam String password, HttpServletRequest request,HttpSession session) {
        UserEntity users = this.user(request);
        if (StringUtils.isBlank(oldPassword) || StringUtils.isBlank(password)) {
            return APIResponse.fail("请确认信息输入完整");
        }

        if (!users.getPassword().equals(TaleUtils.MD5encode(users.getUsername() + oldPassword))) {
            return APIResponse.fail("旧密码错误");
        }
        if (password.length() < 6 || password.length() > 14) {
            return APIResponse.fail("请输入6-14位密码");
        }

        try {
            UserEntity temp = new UserEntity();
            temp.setId(users.getId());
            String pwd = TaleUtils.MD5encode(users.getUsername() + password);
            temp.setPassword(pwd);
            userService.updateUserInfo(temp);
            logService.addLog(LogActions.UP_PWD.getAction(), null, request.getRemoteAddr(), this.getUid(request));

            //更新session中的数据
            UserEntity original= (UserEntity)session.getAttribute(WebConst.LOGIN_SESSION_KEY);
            original.setPassword(pwd);
            session.setAttribute(WebConst.LOGIN_SESSION_KEY,original);
            return APIResponse.success();
        } catch (Exception e){
            String msg = "密码修改失败";
            if (e instanceof BusinessException) {
                msg = e.getMessage();
            } else {
                LOGGER.error(msg, e);
            }
            return APIResponse.fail(msg);
        }
    }
}
