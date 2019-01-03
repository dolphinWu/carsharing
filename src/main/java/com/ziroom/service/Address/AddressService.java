package com.ziroom.service.Address;

import com.ziroom.model.AddressEntity;

import java.util.List;

public interface AddressService {
    AddressEntity findAddressById(Integer id);

    List<AddressEntity> findAddressByUid(String uid);

   int  deleteAddressById(Integer id);

   int updateAddressById(AddressEntity address);

   int insertAddress(AddressEntity address);
}
