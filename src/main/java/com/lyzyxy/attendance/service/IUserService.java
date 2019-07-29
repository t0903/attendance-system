package com.lyzyxy.attendance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyzyxy.attendance.dto.UserDto;
import com.lyzyxy.attendance.model.Sign;
import com.lyzyxy.attendance.model.User;

import java.util.List;
import java.util.Map;

public interface IUserService extends IService<User> {
    List<Map<String, Object>> test();
    List<UserDto> attendance(int courseId);
    boolean cancelSign(int courseId);
    boolean sign(Sign sign);
}
