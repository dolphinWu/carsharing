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

    private String employeeNo;

    private String remark;

    private String homeAddressName;

    private String homeLongitude;

    private String homeLatitude;

    private String companyAddressName;

    private String companyLongitude;

    private String companyLatitude;

    private String jobTitle;

}