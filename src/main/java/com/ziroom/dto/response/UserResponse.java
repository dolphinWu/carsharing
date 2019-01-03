package com.ziroom.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponse {

    //员工系统号
    private String emplid;

    //客户姓名
    private String name;

    //客户手机号
    private String phone;

    //职级名称
    private String levelName;

    //邮箱
    private String email;

    private String carNumber;

    private Integer creditScore;

    private Integer homeAddressId;

    private Integer companyAddressId;
}
