package com.ziroom.controller.admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ziroom.config.CasConfig;
import com.ziroom.config.EhrConfig;
import com.ziroom.dto.response.UserDetail;
import com.ziroom.dto.response.UserResponse;
import com.ziroom.http.HttpRequestClient;
import com.ziroom.model.UserEntity;
import com.ziroom.service.user.UserService;
import com.ziroom.utils.APIResponse;
import io.swagger.annotations.Api;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("用户管理类")
@Controller
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private CasConfig casConfig;

    @Autowired
    private EhrConfig ehrConfig;

    @Autowired
    private UserService userService;

    @GetMapping("/Hello")
    @ResponseBody
    public APIResponse<String> hello(){
        return APIResponse.success( "Hello ZiRoomer");
    }


    /**
     * cas 登录
     * @param userName
     * @param password
     * @return
     */
    @PostMapping(value="/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public APIResponse login(String userName,String password){
        String  companyFlag="0";//0自如 1北京链家 2上海链家
        String casLoginUrl = casConfig.getGetToken();
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("userName",userName);
        paramMap.put("password",password);
        paramMap.put("companyFlag",companyFlag);
        paramMap.put("appType","1");
        LOGGER.info("CasLogin url:{}", casLoginUrl);
        try {
            HttpRequest httpRequest = HttpRequest.post(casLoginUrl).query(paramMap);
            HttpResponse response = httpRequest.send();
            if (response.statusCode() == 200 && response.bodyBytes() != null){
                String token =response.header("token");
                byte[] bytes = response.bodyBytes();
                String s = new String(bytes,"utf-8");
                LOGGER.info("CasLogin 返回的结果为==>" + s);
                JSONObject jsonResult = JSONObject.parseObject(s);
                //登录成功   获取用户信息
                return queryUserDetail(jsonResult.getString("employeeId"), token);
            } else {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            LOGGER.info("登录错误结果==>" + e.getMessage());
            return APIResponse.fail("调用CAS登录异常");
        }
    }

    /**
     * token认证
     * @param token
     * @return
     */
    @PostMapping(value="/authToken", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public APIResponse authToken(String token){
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("token",token);
        String url = casConfig.getAuthToken();
        LOGGER.info("调用authToken接口的url:{}", url);
        String returnString = HttpRequestClient.postRequest(url, paramMap);
        LOGGER.info("调用authToken接口结果:{}", returnString);
        return  APIResponse.success(returnString);
    }


    /**
     * 通过ehr查询用户信息
     * @param  employeeId 系统号
     * @return
     */
    @PostMapping(value="/queryUserDetail", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public APIResponse queryUserDetail(String employeeId, String token){
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("userCode",employeeId);
        paramMap.put("page","1");
        paramMap.put("size","5");
        String url = ehrConfig.getGetUserDetil();
        LOGGER.info("调用authToken接口的url:{}", url);
        String returnString = HttpRequestClient.sendGet(url, paramMap);
        LOGGER.info("调用authToken接口结果:{}", returnString);
        //解析数据
        JSONObject jsonResult = JSONObject.parseObject(returnString);
        if (!"success".equals(jsonResult.getString("status"))) {
            return  APIResponse.fail(jsonResult.getString("message"));
        }
        List<UserResponse> json = JSONArray.parseArray(jsonResult.getString("data"), UserResponse.class);
        UserResponse response = json.get(0);
        if(CollectionUtils.isEmpty(json) || response==null){
            return APIResponse.fail("调用ehr系统失败");
        }
        UserEntity user = userService.getUserInfoByEmployeeNo(response.getEmplid());
        if(user==null ){
            user = new UserEntity();
            //同步信息到数据库
            user.setUname(response.getName());
            user.setEmployeeNo(response.getEmplid());
            user.setUid(response.getEmplid());
            user.setPhoneNumber(response.getPhone());
            user.setCreditScore(100);//信用分
            userService.insertUserInfo(user);
        }
        try {
            BeanUtils.copyProperties(response, user);
        } catch (Exception e) {
            LOGGER.info("调用copyProperties结果:{}", e.getMessage());
        }
        response.setToken(token);
        return  APIResponse.success(response);
    }



    /**
     * 修改车牌号
     * @param  employNo 系统号
     * @return
     */
    @PostMapping(value="/modifyOrAddCarNo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public APIResponse modifyOrAddCarNo(String employNo,String carNumber){
       UserEntity userEntity =  userService.getUserInfoByEmployeeNo(employNo);
       if(userEntity==null){
           return  APIResponse.fail("该用户不存在");
       }
        userEntity.setCarNumber(carNumber);
        userService.updateCarNoByEmployeeNo(userEntity);
        return  APIResponse.success();
    }


    /**
     * 查询用户信息
     * @param  employeeNo 系统号
     * @return
     */
    @PostMapping(value="/getUserDetail", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public APIResponse getUserDetail(String employeeNo){
        UserDetail response=new UserDetail();
        UserEntity user = userService.getUserInfoByEmployeeNo(employeeNo);
        try {
            BeanUtils.copyProperties(response, user);
        } catch (Exception e) {
            LOGGER.info("调用copyProperties结果:{}", e.getMessage());
        }
        //查询累计手收益
        int amount = 0;
        response.setAmount(amount);
        return  APIResponse.success(user);
    }

}
