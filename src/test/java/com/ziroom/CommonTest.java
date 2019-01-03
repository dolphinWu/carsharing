package com.ziroom;

import com.ziroom.dto.PPointDistance;
import com.ziroom.utils.GsonUtils;
import com.ziroom.utils.MathUtil;
import org.junit.Test;

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
}
