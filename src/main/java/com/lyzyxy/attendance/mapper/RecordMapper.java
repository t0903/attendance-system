package com.lyzyxy.attendance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyzyxy.attendance.dto.RecordDto;
import com.lyzyxy.attendance.model.Course;
import com.lyzyxy.attendance.model.Record;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RecordMapper extends BaseMapper<Record> {
    @Select("SELECT a.*,sum FROM(" +
            "SELECT * FROM record WHERE courseId=#{courseId} AND END IS NOT NULL) a," +
            "(SELECT COUNT(*) AS SUM  FROM sc WHERE classId = #{courseId}) b")
    List<RecordDto> getSignRecords(@Param("courseId") int courseId);
}
