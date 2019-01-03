package com.ziroom.service.Address.impl;

import com.ziroom.dao.AddressEntityMapper;
import com.ziroom.model.AddressEntity;
import com.ziroom.service.Address.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressEntityMapper addressEntityMapper;

    @Override
    public AddressEntity findAddressById(Integer addressId) {
        return addressEntityMapper.selectByPrimaryKey(addressId);
    }

    @Override
    public List<AddressEntity> findAddressByUid(String uid) {
        return addressEntityMapper.selectListByUid(uid);
    }

   public int  deleteAddressById(Integer id){
        return addressEntityMapper.deleteByPrimaryKey(id);
    }

   public int updateAddressById(AddressEntity addressEntity){
       return addressEntityMapper.updateByPrimaryKey(addressEntity);
    }

    public int insertAddress(AddressEntity address){
        return addressEntityMapper.insertSelective(address);
    }
}
