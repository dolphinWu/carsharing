package com.ziroom.utils;


import org.springframework.stereotype.Component;

/**
 * 后台公共函数
 */
@Component
public final class AdminCommons {

    private static final String[] COLORS = {"default", "primary", "success", "info", "warning", "danger", "inverse", "purple", "pink"};

    public static String rand_color() {
        int r = Tools.rand(0, COLORS.length - 1);
        return COLORS[r];
    }

}
