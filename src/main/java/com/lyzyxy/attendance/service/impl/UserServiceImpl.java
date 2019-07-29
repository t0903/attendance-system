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

    @Transactional(readOnly = false)
    public boolean sign(Sign sign){
        if(sign.insert()){
            UpdateWrapper<Record> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda().eq(Record::getId,sign.getRecordId()).setSql("count = count + 1");

            return recordService.update(updateWrapper);
        }
        return false;
    }

    @Transactional(readOnly = false)
    public boolean cancelSign(int courseId){
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .lambda()
                .eq(Record::getCourseId,courseId)
                .isNull(Record::getEnd);

        Record record = recordService.getOne(queryWrapper);

        UpdateWrapper<Sign> signUpdateWrapper = new UpdateWrapper<>();
        signUpdateWrapper
                .lambda()
                .eq(Sign::getRecordId,record.getId());

        signService.remove(signUpdateWrapper);

        return recordService.removeById(record.getId());
    }
}
