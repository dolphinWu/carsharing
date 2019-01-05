package com.ziroom.dto.request;

import com.ziroom.utils.MathUtil;
import com.ziroom.utils.PointCalculateUtil;
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
        String xXDistance = PointCalculateUtil.num1SubtractNum2(this.endXpoint,this.startXpoint)+"";
        String yYDistance = PointCalculateUtil.num1SubtractNum2(this.endYpoint,this.startYpoint)+"";

        //x差值的平方
        String x2 = PointCalculateUtil.num1MultiplyNum2(xXDistance, xXDistance)+"";
        String y2 = PointCalculateUtil.num1MultiplyNum2(yYDistance, yYDistance) + "";
        //平方和
        String z2 = PointCalculateUtil.num1PlusNum2(x2, y2);
        return PointCalculateUtil.square(z2);
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
