package com.lyzyxy.attendance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyzyxy.attendance.dto.RecordDto;
import com.lyzyxy.attendance.model.Course;
import com.lyzyxy.attendance.model.Record;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RecordMapper extends BaseMapper<Record> {
    /**
     * 获取签到记录，同时获取每次签到人数，及选课的总人数
     * @param courseId
     * @return
     */
    @Select("SELECT a.*,sum FROM( " +
            "SELECT re.*,count FROM record re left join " +
            "(SELECT r.id,COUNT(*) COUNT FROM SIGN s LEFT JOIN record r ON s.recordId = r.id " +
            "where end is not null AND remarks IS NULL " +
            "GROUP BY r.id) c on re.id = c.id " +
            "WHERE courseId=#{courseId} AND END IS NOT NULL) a, " +
            "(SELECT COUNT(*) AS SUM  FROM sc WHERE classId = #{courseId}) b ORDER BY END DESC")
    List<RecordDto> getSignRecords(@Param("courseId") int courseId);
}
