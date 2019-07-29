package com.lyzyxy.attendance.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class RecordDto {
    private int id;
    private int courseId;
    private Date start;
    private Date end;
    private int count;
    private String location;
    private int sum;
}
