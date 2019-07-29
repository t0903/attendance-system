package com.lyzyxy.attendance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyzyxy.attendance.dto.RecordDto;
import com.lyzyxy.attendance.dto.SignResult;
import com.lyzyxy.attendance.model.Sign;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISignService extends IService<Sign> {
    List<SignResult> getSignResults(int courseId, int recordId);
}
