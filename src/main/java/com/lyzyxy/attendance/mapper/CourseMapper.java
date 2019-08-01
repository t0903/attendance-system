package com.lyzyxy.attendance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyzyxy.attendance.model.Course;
import javafx.scene.control.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CourseMapper extends BaseMapper<Course> {
    /**
     * 不需修改
     * 获取某个学生的所选课程列表
     * @param studentId
     * @return
     */
    @Select("select c.* from course c,sc where c.id = sc.classId and sc.studentid = #{studentId}")
    List<Course> getJoinedCourses(@Param("studentId") int studentId);
}
