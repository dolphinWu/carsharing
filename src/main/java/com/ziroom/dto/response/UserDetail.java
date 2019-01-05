package com.ziroom.dto.response;

import com.ziroom.model.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class UserDetail extends UserEntity{
    private BigDecimal amount;//累计收益
}
