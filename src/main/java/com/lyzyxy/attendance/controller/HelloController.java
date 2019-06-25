package com.lyzyxy.attendance.controller;

import com.lyzyxy.attendance.base.Result;
import com.lyzyxy.attendance.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public Result hello(){
        User u = new User();
        u.setId(1);
        u.setUsername("张三");
        u.setPassword("1111");


        return Result.success(u);
    }

    @RequestMapping("/list")
    public Result list(){
        User u = new User();
        u.setId(1);
        u.setUsername("张三");
        u.setPassword("1111");

        User u2 =  new User();
        u2.setId(2);
        u.setUsername("张");
        u.setPassword("1111");

        List<User> list = new ArrayList<>();
        list.add(u);
        list.add(u2);

        return Result.success(list);
    }
}
