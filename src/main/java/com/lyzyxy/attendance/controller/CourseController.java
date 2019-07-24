package com.lyzyxy.attendance.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyzyxy.attendance.base.Result;
import com.lyzyxy.attendance.model.Course;
import com.lyzyxy.attendance.service.ICourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private ICourseService courseService;

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @RequestMapping("/add")
    public Result add(String name, String className,int teacherId){
        Course c = new Course();
        c.setName(name);
        c.setClassName(className);
        c.setTeacherId(teacherId);

        if(courseService.save(c)){
            return Result.success(c);
        }else{
            return Result.error(c);
        }
    }

    @RequestMapping("/getCourses")
    public Result getCourseByTeacher(int id){
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Course::getTeacherId, id);

        List<Course> courses = courseService.list(queryWrapper);

        return Result.success(courses);
    }

    @RequestMapping("/getJoinedCourses")
    public Result getJoinedCourses(int id){
        List<Course> courses = courseService.getJoinedCourses(id);

        return Result.success(courses);
    }
}
