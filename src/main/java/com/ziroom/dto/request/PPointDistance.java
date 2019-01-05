package com.ziroom.dto.request;

import com.ziroom.utils.MathUtil;
import com.ziroom.utils.Tools;
import org.apache.commons.lang3.math.NumberUtils;

import java.awt.geom.Point2D;

/**
 * @Date:2019/1/2 21:26
 * @Author: liuzh
 * @Description:
 */

public class PPointDistance {
    private String startXpoint	;
    private	String startYpoint;
    private	String endXpoint;
    private	String endYpoint;

    private String unitPrice="1.00";

    public String getStartXpoint() {
        return startXpoint;
    }

    public void setStartXpoint(String startXpoint) {
        this.startXpoint = startXpoint;
    }

    public String getStartYpoint() {
        return startYpoint;
    }

    public void setStartYpoint(String startYpoint) {
        this.startYpoint = startYpoint;
    }

    public String getEndXpoint() {
        return endXpoint;
    }

    public void setEndXpoint(String endXpoint) {
        this.endXpoint = endXpoint;
    }

    public String getEndYpoint() {
        return endYpoint;
    }

    public void setEndYpoint(String endYpoint) {
        this.endYpoint = endYpoint;
    }

    public String totalPrice() {
        return MathUtil.num1MultiplyNum2(unitPrice, distance())+"";
    }

    public String distance() {
        return Tools.getDistance(new Point2D.Double(NumberUtils.toDouble(this.startXpoint, 0), NumberUtils.toDouble(this.startYpoint, 0)), new Point2D.Double(NumberUtils.toDouble(this.endXpoint, 0), NumberUtils.toDouble(this.endYpoint, 0)))+"";
    }

    @Override
    public String toString() {
        return "PPointDistance{" +
                "startXpoint='" + startXpoint + '\'' +
                ", startYpoint='" + startYpoint + '\'' +
                ", endXpoint='" + endXpoint + '\'' +
                ", endYpoint='" + endYpoint + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                '}';
    }
}
