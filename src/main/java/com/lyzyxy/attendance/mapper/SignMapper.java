package com.lyzyxy.attendance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyzyxy.attendance.dto.RecordDto;
import com.lyzyxy.attendance.dto.SignResult;
import com.lyzyxy.attendance.model.Sign;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SignMapper extends BaseMapper<Sign> {
    /**
     * 不需要修改
     * 获取某次签到记录的具体情况，包括每个学生的识别率、距离等
     * @param courseId
     * @param recordId
     * @return
     */
    @Select("SELECT a.id,a.name,a.no,a.photo,b.id AS signId,b.rate,b.distance,b.remarks FROM(" +
            "SELECT u.* FROM USER u LEFT JOIN sc ON u.id = sc.studentId " +
            "WHERE classId = #{courseId}) a LEFT JOIN " +
            "(" +
            "SELECT * FROM SIGN WHERE recordId = #{recordId}) b ON a.id = b.studentId")
    List<SignResult> getSignResults(@Param("courseId") int courseId, @Param("recordId") int recordId);
}
