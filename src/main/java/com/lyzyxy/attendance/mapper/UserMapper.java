package com.lyzyxy.attendance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyzyxy.attendance.model.User;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User> {
    List<Map<String, Object>> test();
}
