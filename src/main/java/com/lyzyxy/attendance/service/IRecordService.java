package com.lyzyxy.attendance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyzyxy.attendance.dto.RecordDto;
import com.lyzyxy.attendance.model.Record;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IRecordService extends IService<Record> {
    void checkAllSignAsync(int courseId,int recordId);
    List<RecordDto> getSignRecords(int courseId);
}
