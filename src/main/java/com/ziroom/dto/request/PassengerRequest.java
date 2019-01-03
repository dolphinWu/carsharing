package com.ziroom.dto.request;

import java.util.Date;

public class PassengerRequest {

    //出发时间
    private Date departTime;

    public Date getDepartTime() {
        return departTime;
    }

    public void setDepartTime(Date departTime) {
        this.departTime = departTime;
    }
}
