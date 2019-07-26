package com.lyzyxy.attendance;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lyzyxy.attendance.dto.UserDto;
import com.lyzyxy.attendance.model.Record;
import com.lyzyxy.attendance.model.Sign;
import com.lyzyxy.attendance.service.IRecordService;
import com.lyzyxy.attendance.service.IUserService;
import com.lyzyxy.attendance.utils.FaceUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AttendanceSystemApplicationTests {
    @Autowired
    IUserService userService;
    @Autowired
    IRecordService recordService;
    @Test
    public void contextLoads() {
    }

    @Test
    public void testService(){
        Sign sign = new Sign();
        sign.setStudentId(11);
        sign.setRecordId(2);
        sign.setRate(1);
        sign.setDistance(10);
        sign.setTime(new Date());

        boolean r = userService.sign(sign);

        System.out.println(r);
    }
}
