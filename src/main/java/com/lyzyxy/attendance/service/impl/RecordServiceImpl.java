package com.lyzyxy.attendance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyzyxy.attendance.mapper.RecordMapper;
import com.lyzyxy.attendance.model.Record;
import com.lyzyxy.attendance.service.IRecordService;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {

}
