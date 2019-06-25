package com.lyzyxy.attendance.controller;

import com.lyzyxy.attendance.annotation.AuthToken;
import com.lyzyxy.attendance.base.Result;
import com.lyzyxy.attendance.utils.FileUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {
    /**
     * 图片上传
     * @param file
     * @return
     */
//    @PostMapping(value = "/img/upload")
//    //@AuthToken
//    public Result uploadImg(@RequestParam("id") int id,@RequestParam("file") MultipartFile file){
//        //图片上传调用工具类
//        try{
//            //保存图片
//            String path =  FileUtil.saveImage(file);
//
//            if(path != null && !path.equals("")){
//
//            }
//
//
//
//            return Result.success("上传照片成功",path);
//        }catch (Exception e){
//            return Result.error("上传照片失败","");
//        }
//    }

    @PostMapping(value = "/img/delete")
    //@AuthToken
    public Result deleteImg(@RequestParam("path") String path){
        //图片上传调用工具类
        try{
            //保存图片
            boolean r =  FileUtil.deleteImage(path);

            return Result.success("删除照片成功","");
        }catch (Exception e){
            return Result.error("删除照片失败","");
        }
    }
}
