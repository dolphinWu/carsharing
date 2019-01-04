package com.ziroom.dto.response;

import com.ziroom.model.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDetail extends UserEntity{
    private Integer amount;//累计收益
}
