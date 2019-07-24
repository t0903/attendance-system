package com.lyzyxy.attendance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyzyxy.attendance.mapper.CourseMapper;
import com.lyzyxy.attendance.model.Course;
import com.lyzyxy.attendance.service.ICourseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Override
    public List<Course> getJoinedCourses(int studentId) {
        return this.baseMapper.getJoinedCourses(studentId);
    }
}
