package com.ziroom.service.Address.impl;

import com.ziroom.dao.AddressEntityMapper;
import com.ziroom.model.AddressEntity;
import com.ziroom.service.Address.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressEntityMapper addressEntityMapper;

    @Override
    public AddressEntity findAddressById(Integer addressId) {
        return addressEntityMapper.selectByPrimaryKey(addressId);
    }
}
