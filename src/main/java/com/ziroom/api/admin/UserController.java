package com.ziroom.api.admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ziroom.config.CasConfig;
import com.ziroom.config.EhrConfig;
import com.ziroom.dto.response.UserDetail;
import com.ziroom.dto.response.UserResponse;
import com.ziroom.exception.BusinessException;
import com.ziroom.http.HttpRequestClient;
import com.ziroom.model.UserEntity;
import com.ziroom.service.driverOrder.DriverPlanService;
import com.ziroom.service.user.UserService;
import com.ziroom.utils.APIResponse;
import com.ziroom.utils.MathUtil;
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

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("用户管理类")
@Controller
@RequestMapping(value = "/api/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private CasConfig casConfig;

    @Autowired
    private EhrConfig ehrConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private DriverPlanService driverPlanService;

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
                return queryUserDetailByEHR(jsonResult.getString("employeeId"), token);
            } else {
                throw new BusinessException("调用CAS登录异常");
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
    @PostMapping(value="/queryUserDetailByEHR", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public APIResponse queryUserDetailByEHR(String employeeId, String token){
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
            user.setRemark("欢迎登录自如行APP");
            user.setIsDel(0);
            user.setCreateDate(new Date());
            user.setCreateUser(response.getName());
            user.setJobTitle(response.getDescr());
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
     * 修改信息
     * @param  userRequest
     * @return
     */
    @PostMapping(value="/modifyUser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public APIResponse modifyUser(UserEntity userRequest){
        //数据检验
        String regx = "^[\\u4E00-\\u9FA5][\\da-zA-Z]{6}$";
        if(userRequest.getCarNumber()!=null){
            if(!userRequest.getCarNumber().matches(regx)){
                return APIResponse.fail("车牌号不符合规则");
            }
        }
       UserEntity userEntity =  userService.getUserInfoByEmployeeNo(userRequest.getUid());
       if(userEntity==null){
           return  APIResponse.fail("该用户不存在");
       }
       userRequest.setId(userEntity.getId());
       userService.updateUserByUid(userRequest);
       return  APIResponse.success();
    }


    /**
     * 查询用户信息
     * @param  uid 系统号
     * @return
     */
    @PostMapping(value="/queryUserDetail", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public APIResponse queryUserDetail(String uid){
        UserDetail response=new UserDetail();
        UserEntity user = userService.getUserInfoByEmployeeNo(uid);
        try {
            BeanUtils.copyProperties(response, user);
        } catch (Exception e) {
            LOGGER.info("调用copyProperties结果:{}", e.getMessage());
        }
        //查询累计手收益
        Double amount = driverPlanService.sumMoney(uid);
        if (amount == null) {
            amount=0.00;
        }
        response.setAmount(new BigDecimal(MathUtil.formatFloatNumber(amount)).setScale(2,BigDecimal.ROUND_HALF_UP));
        return  APIResponse.success(response);
    }

}
