package com.lyzyxy.attendance.dto;

import lombok.Data;

@Data
public class SignResult {
    private int id;
    private String name;
    private String no;
    private String photo;
    private Integer signId;
    private Double rate;//图像识别正确率
    private Integer distance;
    private String remarks;
}
