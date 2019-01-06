package com.ziroom.dto.response;

import com.ziroom.model.PassengerOrderEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by codey on 2019/1/6.
 */
@Setter
@Getter
public class PassengerOrderResponse extends PassengerOrderEntity {

    private String uname;

    private String phoneNumber;
}
