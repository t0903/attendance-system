package com.lyzyxy.attendance.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

@TableName("user")
public class User extends Model<User> {
    @TableId(value="id", type= IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String no;
    private String photo;
    @TableField(value = "isTeacher")
    private int isTeacher;
    @TableField(exist = false)
    private String token;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getIsTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(int isTeacher) {
        this.isTeacher = isTeacher;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
