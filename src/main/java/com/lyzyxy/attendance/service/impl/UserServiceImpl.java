package com.lyzyxy.attendance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyzyxy.attendance.mapper.UserMapper;
import com.lyzyxy.attendance.model.User;
import com.lyzyxy.attendance.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Override
    public List<Map<String, Object>> test() {
        return this.baseMapper.test();
    }
}
