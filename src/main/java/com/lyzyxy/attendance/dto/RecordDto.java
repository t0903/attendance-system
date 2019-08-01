package com.lyzyxy.attendance.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class RecordDto {
    private int id;//recordId
    private int courseId;
    private Date start;
    private Date end;
    private Integer count;//签到人数
    private String location;
    private Integer sum;//选课人数
}
