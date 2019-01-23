package com.ziroom;

import com.ziroom.otherDao.elasticesearch.ElasticSearchDao;
import io.searchbox.client.JestResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarsharingApplicationTests {

    @Autowired
    private ElasticSearchDao elasticSearchDao;

    @Test
    public void contextLoads() {
    }


    @Test
    public void esTest() {
        JestResult jestResult = elasticSearchDao.nodesInfo();
        System.out.println(jestResult.getJsonString());

    }
}

