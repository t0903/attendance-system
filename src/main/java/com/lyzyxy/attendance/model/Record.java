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
@TableName("record")
public class Record extends Model<Record> {
    @TableId(value="id", type= IdType.AUTO)
    private Integer id;
    @TableField(value = "courseId")
    private int courseId;
    private Date start;
    private Date end;
    private String location;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
