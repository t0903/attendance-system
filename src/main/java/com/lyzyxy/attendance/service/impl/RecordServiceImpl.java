package com.lyzyxy.attendance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyzyxy.attendance.base.PushMessage;
import com.lyzyxy.attendance.dto.RecordDto;
import com.lyzyxy.attendance.mapper.RecordMapper;
import com.lyzyxy.attendance.mapper.StudentCourseMapper;
import com.lyzyxy.attendance.model.Record;
import com.lyzyxy.attendance.model.Sign;
import com.lyzyxy.attendance.model.StudentCourse;
import com.lyzyxy.attendance.service.IRecordService;
import com.lyzyxy.attendance.service.ISignService;
import com.lyzyxy.attendance.service.ISocketIOService;
import com.lyzyxy.attendance.service.IStudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {
    @Autowired
    private IStudentCourseService studentCourseService;
    @Autowired
    private ISignService signService;
    @Autowired
    private ISocketIOService socketIOService;

    @Async
    public void checkAllSignAsync(int courseId,int recordId){
        int n = signService.getSignCount(recordId);

        QueryWrapper<StudentCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(StudentCourse::getClassId,courseId);
        int s = studentCourseService.count(queryWrapper);

        if(s == n){
            UpdateWrapper<Record> updateWrapper = new UpdateWrapper<>();
            updateWrapper
                    .lambda()
                    .eq(Record::getId,recordId)
                    .set(Record::getEnd,new Date());

            this.update(updateWrapper);

            PushMessage message = new PushMessage(""+courseId,1,"共"+s+"人签到完成!");
            socketIOService.pushMessageToUser(message);
        }
    }

    public List<RecordDto> getSignRecords(int courseId){
        return this.baseMapper.getSignRecords(courseId);
    }
}
