package com.ziroom.utils;

import com.ziroom.dao.UserEntityMapper;
import com.ziroom.model.UserEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class UserUtils {

    /**
     * 获取当前用户
     * @return
     */
    public static UserEntity getCurrentUser() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String uid = request.getParameter("uid");
        UserEntityMapper userEntityMapper = SpringUtils.getBean(UserEntityMapper.class);
        return userEntityMapper.selectByUId(uid);
    }

}
