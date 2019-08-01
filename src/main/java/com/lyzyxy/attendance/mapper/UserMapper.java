package com.lyzyxy.attendance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyzyxy.attendance.dto.UserDto;
import com.lyzyxy.attendance.model.User;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User> {
    List<Map<String, Object>> test();

    /**
     * 获取每个学生的签到情况，包括签到率、缺勤率
     * @param courseId
     * @return
     */
    List<UserDto> attendance(int courseId);
}
