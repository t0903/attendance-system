package com.lyzyxy.attendance.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sign")
public class Sign extends Model<Sign> {
    @TableId(value="id", type= IdType.AUTO)
    private Integer id;
    @TableField(value = "recordId")
    private int recordId;
    @TableField(value = "studentId")
    private int studentId;
    private Date time;
    private double rate;
    private int distance;
    private String remarks;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
