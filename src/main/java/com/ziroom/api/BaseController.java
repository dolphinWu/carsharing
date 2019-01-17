package com.ziroom.api;

import com.ziroom.model.UserEntity;
import com.ziroom.utils.MapCache;
import com.ziroom.utils.TaleUtils;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseController {

    protected MapCache cache = MapCache.single();

    /**
     * 获取请求绑定的登录对象
     * @param request
     * @return
     */
    public UserEntity user(HttpServletRequest request) {
        return TaleUtils.getLoginUser(request);
    }

    public Integer getUid(HttpServletRequest request){
        return this.user(request).getId();
    }

    /**
     * 数组转字符串
     *
     * @param arr
     * @return
     */
    public String join(String[] arr) {
        StringBuilder ret = new StringBuilder();
        String[] var3 = arr;
        int var4 = arr.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String item = var3[var5];
            ret.append(',').append(item);
        }

        return ret.length() > 0 ? ret.substring(1) : ret.toString();
    }
}
