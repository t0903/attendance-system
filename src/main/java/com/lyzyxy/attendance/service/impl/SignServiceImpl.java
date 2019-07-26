package com.lyzyxy.attendance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyzyxy.attendance.mapper.SignMapper;
import com.lyzyxy.attendance.model.Sign;
import com.lyzyxy.attendance.service.ISignService;
import org.springframework.stereotype.Service;

@Service
public class SignServiceImpl extends ServiceImpl<SignMapper, Sign> implements ISignService {

}
