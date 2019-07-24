package com.lyzyxy.attendance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyzyxy.attendance.model.Course;
import com.lyzyxy.attendance.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ICourseService extends IService<Course> {
    List<Course> getJoinedCourses(int studentId);
}
