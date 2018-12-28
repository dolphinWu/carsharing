package com.ziroom.constant;

public interface ErrorConstant {

    interface Common {
        static final String PARAM_IS_EMPTY = "参数为空";
        static final String INVALID_PARAM = "无效的参数";
        static final String CAN_NOT_FIND_PARAM_TO_CONTIUNE = "找不到参数继续运行";
    }

    interface Auth {
        static final String USERNAME_PASSWORD_IS_EMPTY = "用户名和密码不可为空";
        static final String USERNAME_PASSWORD_ERROR = "用户名不存在或密码错误";
        static final String NOT_LOGIN = "用户未登录";
    }

}
