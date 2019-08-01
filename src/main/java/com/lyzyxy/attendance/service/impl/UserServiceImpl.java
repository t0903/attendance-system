package com.lyzyxy.attendance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyzyxy.attendance.base.Result;
import com.lyzyxy.attendance.dto.UserDto;
import com.lyzyxy.attendance.mapper.UserMapper;
import com.lyzyxy.attendance.model.Record;
import com.lyzyxy.attendance.model.Sign;
import com.lyzyxy.attendance.model.User;
import com.lyzyxy.attendance.service.IRecordService;
import com.lyzyxy.attendance.service.ISignService;
import com.lyzyxy.attendance.service.IUserService;
import com.sun.deploy.net.proxy.pac.PACFunctionsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private IRecordService recordService;
    @Autowired
    private ISignService signService;

    @Override
    public List<Map<String, Object>> test() {
        return this.baseMapper.test();
    }

    public List<UserDto> attendance(int courseId){
        return this.baseMapper.attendance(courseId);
    }

    public boolean sign(Sign sign){
        return sign.insert();
    }

    @Transactional(readOnly = false)
    public boolean cancelSign(int recordId){
        UpdateWrapper<Sign> signUpdateWrapper = new UpdateWrapper<>();
        signUpdateWrapper
                .lambda()
                .eq(Sign::getRecordId,recordId);

        signService.remove(signUpdateWrapper);

        return recordService.removeById(recordId);
    }
}
