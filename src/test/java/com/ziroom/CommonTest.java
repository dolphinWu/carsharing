package com.ziroom;

import com.ziroom.dto.request.PPointDistance;
import com.ziroom.utils.GsonUtils;
import com.ziroom.utils.MathUtil;
import com.ziroom.utils.PriceCalculateUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Date:2019/1/2 21:45
 * @Author: liuzh
 * @Description:
 */
public class CommonTest {

    /**
     * @author liuzh
     * @description:测试开方
     * @date 2019/1/2 21:45
     * @param
     * @return
     */
    @Test
    public void testSqual() {
        for (int i = 0; i < 10000; i++) {
            Double randomDoublex1 = MathUtil.getRandomDouble();
            Double randomDoublex2 = MathUtil.getRandomDouble();
            Double randomDoubley1 = MathUtil.getRandomDouble();
            Double randomDoubley2 = MathUtil.getRandomDouble();

            int randomIntx1 = MathUtil.getRandomInt(i+1);
            int randomIntx2 = MathUtil.getRandomInt(i+1);
            int randomInty1 = MathUtil.getRandomInt(i+1);
            int randomInty2 = MathUtil.getRandomInt(i+1);

            PPointDistance pPointDistance = new PPointDistance();

            pPointDistance.setStartXpoint(randomDoublex1+randomIntx1+"");
            pPointDistance.setStartYpoint(randomDoublex2+randomIntx2+"");
            pPointDistance.setEndXpoint(randomDoubley1+randomInty1+"");
            pPointDistance.setEndYpoint(randomDoubley2+randomInty2+"");
            System.out.println(GsonUtils.toJsonString(pPointDistance));
            System.out.println(pPointDistance+"distance="+pPointDistance.distance());

        }
    }

    @Test
    public void testPriceCal() {

        //起点
        Point2D pointDS = new Point2D.Double(23.00, 24.00);
        //终点
        Point2D pointDE = new Point2D.Double(23.08, 24.00);
        //乘客终点1
        Point2D pointPE1 = new Point2D.Double(23.02, 24.00);
        //乘客终点2
        Point2D pointPE2 = new Point2D.Double(23.04, 24.00);
        //乘客终点3
        Point2D pointPE3 = new Point2D.Double(23.08, 24.00);

        List<Point2D> point2DS = new ArrayList<Point2D>();
        point2DS.add(pointPE1);
        point2DS.add(pointPE2);
        point2DS.add(pointPE3);

        Map<Point2D, String> point2DStringMap = PriceCalculateUtil.calculatePrice("100", point2DS, pointDS, pointDE,1);
        System.out.println(point2DStringMap);
    }


    @Test
    public void testZero() {

        String s = MathUtil.num1DivideNum2("12", "0");
        System.out.println(s);
    }
}
