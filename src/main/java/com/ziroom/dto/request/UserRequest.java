package com.ziroom.dto.request;

import com.ziroom.model.AddressEntity;
import com.ziroom.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserRequest extends BaseEntity {

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

}
