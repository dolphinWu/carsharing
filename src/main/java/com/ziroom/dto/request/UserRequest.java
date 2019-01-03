package com.ziroom.dto.request;

import com.ziroom.model.AddressEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest  extends AddressEntity {

    private String employeeNo;
    private String uid;
}
