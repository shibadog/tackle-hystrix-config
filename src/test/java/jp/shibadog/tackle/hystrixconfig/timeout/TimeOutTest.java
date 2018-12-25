package jp.shibadog.tackle.hystrixconfig.timeout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@SpringJUnitConfig
public class TimeOutTest {

    @Autowired
    private TimeOutHystrix service;

    @Test
    public void test() {
        service.execute();
    }
    
    @Test
    public void test2() {
        service.execute(1L);
    }
}