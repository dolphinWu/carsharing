package com.ziroom.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ehr配置
 */
@Component
@Getter
@Setter
public class EhrConfig {
    @Value("${ehr.url.userDetil}")
    private String getUserDetil;

}
