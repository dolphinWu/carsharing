package com.ziroom.config;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * cas配置
 */
@Component
@Getter
@Setter
public class CasConfig {

    @Value("${cas.url.getToken}")
    private String getToken;//登录获取token

    @Value("${cas.url.authToken}")
    private String authToken;//认证

    @Value("${cas.url.authToken}")
    private String queryUserInfo;//查询用户信息

}
