package com.ziroom.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEntity extends BaseEntity {
    private Integer id;

    private String uid;

    private String uname;

    private String phoneNumber;

    private String carNumber;

    private Integer creditScore;

    private Integer homeAddressId;

    private Integer companyAddressId;

    private String employeeNo;

    private String remark;

}