package com.lyzyxy.attendance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyzyxy.attendance.model.Record;

public interface IRecordService extends IService<Record> {
    void checkAllSignAsync(int courseId,int recordId);
}
