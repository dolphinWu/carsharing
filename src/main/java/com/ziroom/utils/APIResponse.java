package com.ziroom.utils;

/**
 * 返回的参数封装类
 */
public class APIResponse <T> {

    private static final Integer CODE_SUCCESS = 0;

    private static final Integer CODE_FAIL = -1;

    private Integer code;
    private T data;
    private String msg;

    public APIResponse(){

    }

    public APIResponse(Integer code){
        this.code = code;
    }

    public APIResponse(Integer code, T data){
        this.code = code;
        this.data = data;
    }

    public APIResponse(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public static APIResponse success(){
        return new APIResponse(CODE_SUCCESS);
    }

    public static APIResponse success(Object data){
        return new APIResponse(CODE_SUCCESS, data);
    }

    public static APIResponse fail(String msg){
        return new APIResponse(CODE_FAIL, msg);
    }

    public static APIResponse widthCode(Integer errorCode) {
        return new APIResponse(errorCode);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
