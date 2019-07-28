package com.lyzyxy.attendance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyzyxy.attendance.base.PushMessage;
import com.lyzyxy.attendance.mapper.RecordMapper;
import com.lyzyxy.attendance.mapper.StudentCourseMapper;
import com.lyzyxy.attendance.model.Record;
import com.lyzyxy.attendance.model.StudentCourse;
import com.lyzyxy.attendance.service.IRecordService;
import com.lyzyxy.attendance.service.ISocketIOService;
import com.lyzyxy.attendance.service.IStudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {
    @Autowired
    private IStudentCourseService studentCourseService;
    @Autowired
    private ISocketIOService socketIOService;

    @Async
    public void checkAllSignAsync(int courseId,int recordId){
        Record record = getById(recordId);

        QueryWrapper<StudentCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(StudentCourse::getClassId,courseId);
        int s = studentCourseService.count(queryWrapper);

        if(record != null && s == record.getCount()){
            record.setEnd(new Date());
            record.updateById();

            PushMessage message = new PushMessage(""+courseId,1,"共"+s+"人签到完成!");
            socketIOService.pushMessageToUser(message);
        }
    }
}
