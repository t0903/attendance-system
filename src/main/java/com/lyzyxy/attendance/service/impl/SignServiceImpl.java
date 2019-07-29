package com.lyzyxy.attendance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyzyxy.attendance.dto.SignResult;
import com.lyzyxy.attendance.mapper.SignMapper;
import com.lyzyxy.attendance.model.Record;
import com.lyzyxy.attendance.model.Sign;
import com.lyzyxy.attendance.service.IRecordService;
import com.lyzyxy.attendance.service.ISignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SignServiceImpl extends ServiceImpl<SignMapper, Sign> implements ISignService {
    @Autowired
    private IRecordService recordService;

    public List<SignResult> getSignResults(int courseId, int recordId){
        return this.baseMapper.getSignResults(courseId,recordId);
    }

    private boolean isSign(int signId){
        Sign sign = getById(signId);

        if(sign != null){
            if(sign.getTime() != null && sign.getRate() != 0 && sign.getRemarks() == null)
                return true;
        }
        return false;//没签到
    }

    private void setUnsign(int recordId,int signId,String msg){
        if(isSign(signId)) {
            UpdateWrapper<Record> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda().eq(Record::getId, recordId).setSql("count = count - 1");
            recordService.update(updateWrapper);
        }

        UpdateWrapper<Sign> signUpdateWrapper = new UpdateWrapper<>();
        signUpdateWrapper
                .lambda()
                .eq(Sign::getId,signId)
                .set(Sign::getRemarks,msg);
        this.update(signUpdateWrapper);
    }

    @Transactional(readOnly = false)
    public void setSign(int recordId,int signId,int studentId,String msg){
        Sign sign = new Sign();
        if(msg.equals("请假") || msg.equals("迟到")){
            if(signId == -1) {
                sign.setRecordId(recordId);
                sign.setStudentId(studentId);
                sign.setRemarks(msg);

                sign.insert();
            }else{
                setUnsign(recordId,signId,msg);
            }
        }else if(msg.equals("早退") || msg.equals("缺勤")){
            setUnsign(recordId,signId,msg);
        }else if(msg.equals("已签到")){
            if(signId == -1) {
                sign.setRecordId(recordId);
                sign.setStudentId(studentId);

                sign.insert();

                UpdateWrapper<Record> updateWrapper = new UpdateWrapper<>();
                updateWrapper.lambda().eq(Record::getId, recordId).setSql("count = count + 1");
                recordService.update(updateWrapper);
            }else{
                if(!isSign(signId)) {
                    UpdateWrapper<Record> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.lambda().eq(Record::getId, recordId).setSql("count = count + 1");
                    recordService.update(updateWrapper);
                }

                UpdateWrapper<Sign> signUpdateWrapper = new UpdateWrapper<>();
                signUpdateWrapper
                        .lambda()
                        .eq(Sign::getId,signId)
                        .set(Sign::getRemarks,null);
                this.update(signUpdateWrapper);
            }
        }
    }
}
