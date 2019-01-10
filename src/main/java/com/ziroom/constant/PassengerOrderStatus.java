package com.ziroom.constant;

public enum PassengerOrderStatus {
    WAIT_DEPART(0, "待发车"),
    TRAVELING(1, "行驶中"),
    COMPLETE(2, "已完成"),
    CANCEL(3, "已取消");

    PassengerOrderStatus(Integer statusCode, String statusName) {
        this.statusCode = statusCode;
        this.statusName = statusName;
    }

    private Integer statusCode;

    private String statusName;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
