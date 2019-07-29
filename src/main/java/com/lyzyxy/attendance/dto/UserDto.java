package com.lyzyxy.attendance.dto;

import lombok.Data;

@Data
public class UserDto {
    private int id;//学生Id
    private String name;
    private String no;
    private String photo;
    private Double rate;
    private Integer loss;
}
