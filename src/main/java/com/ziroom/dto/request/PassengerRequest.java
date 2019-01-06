package com.ziroom.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class PassengerRequest {

    //出发时间
    private Date departTime;

    private String longitude;

    private String latitude;

    private String uid;

    //0下班，1上班
    private Integer useType;

    private Double radius = 2000D;
}
