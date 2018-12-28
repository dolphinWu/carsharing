package com.ziroom.interceptor;

import com.ziroom.constant.WebConst;
import com.ziroom.model.UserEntity;
import com.ziroom.service.user.UserService;
import com.ziroom.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 */
@Component
public class BaseInterceptor implements HandlerInterceptor {
    private static final Logger LOGGE = LoggerFactory.getLogger(BaseInterceptor.class);
    private static final String USER_AGENT = "user-agent";

    @Autowired
    private UserService userService;

    @Autowired
    private Commons commons;

    @Autowired
    private AdminCommons adminCommons;

    private MapCache cache = MapCache.single();


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String uri = request.getRequestURI();
        LOGGE.info("UserAgent: {}", request.getHeader(USER_AGENT));
        LOGGE.info("用户访问地址: {}, 来路地址: {}", uri, IPKit.getIpAddrByRequest(request));
        //请求拦截处理
        UserEntity user = TaleUtils.getLoginUser(request);
        if (null == user) {
            Integer uid = TaleUtils.getCookieUid(request);
            if (null != uid) {
                //这里还是有安全隐患,cookie是可以伪造的
                user = userService.getUserInfoById(uid);
                request.getSession().setAttribute(WebConst.LOGIN_SESSION_KEY, user);
            }
        }
        if (uri.startsWith("/admin") && !uri.startsWith("/admin/login") && null == user
                && !uri.startsWith("/admin/css") && !uri.startsWith("/admin/images")
                && !uri.startsWith("/admin/js") && !uri.startsWith("/admin/plugins")
                && !uri.startsWith("/admin/editormd")) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
