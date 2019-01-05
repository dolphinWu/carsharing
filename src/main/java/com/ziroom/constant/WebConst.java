package com.ziroom.constant;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WebConst {

    /**
     * 一些网站配置
     */
    public static Map<String, String> initConfig = new HashMap<>();

    /**
     * session的key
     */
    public static String LOGIN_SESSION_KEY = "login_user";

    public static final String USER_IN_COOKIE = "S_L_ID";

    /**
     * aes加密加盐
     */
    public static String AES_SALT = "0123456789abcdef";

    /**
     * 上传文件最大1M
     */
    public static Integer MAX_FILE_SIZE = 1048576;
}
