package com.lyzyxy.attendance;

import com.lyzyxy.attendance.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AttendanceSystemApplicationTests {
    @Autowired
    RedisUtils utils;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testRedisUtils(){
        utils.set("test","123456");

        String cc= utils.get("test");

        System.out.println(cc);

    }

}
