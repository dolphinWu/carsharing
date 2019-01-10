package com.ziroom.controller;

import com.ziroom.dto.request.AddressRequest;
import com.ziroom.model.AddressEntity;
import com.ziroom.service.Address.AddressService;
import com.ziroom.utils.APIResponse;
import io.swagger.annotations.Api;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Api("地址管理类")
@Controller
@RequestMapping(value = "/api/address")
public class UserAddressController {
    private static Logger logger = LoggerFactory.getLogger(UserAddressController.class);

    @Autowired
    private AddressService addressService;
    /**
     * 新增地址
     * @param request
     * @return
     */
    @GetMapping(value="/addAddress", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public APIResponse addAddress(@RequestBody AddressRequest request)throws Exception {
        if(request.getUid()==null){
            return APIResponse.fail("uid不能为空");
        }
        AddressEntity address = new AddressEntity();
        BeanUtils.copyProperties(address, request);
        address.setUid(request.getUid());
        address.setCreateUser(request.getUid());
        address.setCreateDate(new Date());
        address.setLastModifyDate(new Date());
        address.setLastModifyUser(request.getUid());

        int i = addressService.insertAddress(address);
        if(i!=1){
            return APIResponse.fail("新增地址失败");
        }
        return APIResponse.success();
    }

    /**
     * 修改地址
     * @param request
     * @return
     */
    @GetMapping(value="/updateAddress", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public APIResponse updateAddress(@RequestBody AddressRequest request)  throws Exception {
        Integer addressId = request.getId();
        if(addressId==0){
            return APIResponse.fail("用户id不能为空");
        }
        AddressEntity addressEntity = addressService.findAddressById(addressId);
        if(addressEntity==null){
            return APIResponse.fail("地址不存在");
        }

        AddressEntity address = new AddressEntity();
        address.setId(addressEntity.getId());
        BeanUtils.copyProperties(address, request);

        int i = addressService.updateAddressById(address);
        if(i!=1){
            return APIResponse.fail("修改地址失败");
        }
        return APIResponse.success();
    }

    /**
     * 查询地址
     * @param request
     * @return
     */
    @PostMapping(value="/queryAddress", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public APIResponse queryAddress(@RequestBody AddressRequest request){
        String uid = request.getUid();
        if(uid==null){
            return APIResponse.fail("用户uid不能为空");
        }
        List<AddressEntity> list = addressService.findAddressByUid(uid);
        return APIResponse.success(list);

    }

    /**
     * 删除地址
     * @param request
     * @return
     */
    @GetMapping(value="/deleteAddress", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public APIResponse deleteAddress(@RequestBody AddressRequest request){

        Integer addressId = request.getId();
        if(addressId==0){
            return APIResponse.fail("用户id不能为空");
        }
        AddressEntity addressEntity = addressService.findAddressById(addressId);
        if(addressEntity==null){
            return APIResponse.fail("地址不存在");
        }

        int i = addressService.deleteAddressById(addressId);
        if(i!=1){
            return APIResponse.fail("删除地址失败");
        }
        return APIResponse.success();
    }
}
