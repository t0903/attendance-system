package com.lyzyxy.attendance.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
    /**
     * 图片存储 完整路径（{user.home}/upload/images/XXX.jpg）
     * @param file
     * @return 返回相对路径
     */
    public static String saveImage(MultipartFile file) {
        //获取文件上传的根目录 C:\Users\wanghao/upload/image
        String  path = ConstantKit.UPLOAD_PATH + ConstantKit.IMG_FILE_NAME;

        //拿到文件的后缀名和UUID进行拼接形成新的文件名
        //4ca64e85b1544c96b4a6154bb521476f.jpg
        String saveName = CommonUtil.getUuid() + "." + getFileSuffix(file.getOriginalFilename());

        logger.info(" --- 图片保存路径：{}, 图片保存名称：{} --- ", path, saveName);

        // 保存
        try {
            // 保存文件图片
            File targetFile = new File(path);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            file.transferTo(new File(path + "/" + saveName));
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("--- 图片保存异常：{} ---" + e.getMessage());
            return null;
        }
        String filePath =  ConstantKit.VIRTUAL_IMG_PATH;;
        //返回相对路径  img/virtual/4ca64e85b1544c96b4a6154bb521476f.jpg
        return filePath + "/" + saveName;
    }

    public static String encodeBase64(byte[] val){
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String base64 = base64Encoder.encode(val);

        return base64;
    }

    public static String encodeBase64(String path) {
        if(path == null || path.equals(""))
            return null;

        String[] s = path.split("/");
        if(s != null && s.length > 0){
            String name = s[s.length-1];

            String  filePath = ConstantKit.UPLOAD_PATH + ConstantKit.IMG_FILE_NAME;

            try {

                File file = new File(filePath + "/" + name);
                if (file.exists() && file.isFile()) {
                    FileInputStream inputFile = new FileInputStream(file);
                    byte[] buffer = new byte[(int)file.length()];
                    inputFile.read(buffer);
                    inputFile.close();
                    return new BASE64Encoder().encode(buffer);
                }
            }catch (Exception e){
                e.printStackTrace();
                logger.debug("--- 图片转换异常：{} ---" + e.getMessage());
                return null;
            }
        }
        return null;
    }

    /**
     * 删除图片
     * @param path 相对路径
     * @return
     */
    public static boolean deleteImage(String path){
        if(path == null || path.equals(""))
            return true;

        String[] s = path.split("/");
        if(s != null && s.length > 0){
            String name = s[s.length-1];

            String  filePath = ConstantKit.UPLOAD_PATH + ConstantKit.IMG_FILE_NAME;

            try {

                File file = new File(filePath + "/" + name);
                if (file.exists() && file.isFile()) {
                    return file.delete();
                }
            }catch (Exception e){
                e.printStackTrace();
                logger.debug("--- 图片删除异常：{} ---" + e.getMessage());
                return false;
            }
        }
        return false;
    }
    /**
     * 返回截取的文件后缀
     * @param path
     * @return
     */
    private static String getFileSuffix(String path) {
        return getFileSuffix(path, "2");
    }

    /**
     * 获取文件名称或后缀(最后一个"."之后内容)
     * @param path
     * @param type 1名称 2后缀
     * @return
     */
    private static String getFileSuffix(String path, String type){
        if(StringUtils.isNotEmpty(path) && path.indexOf(".") > 0) {
            // 名称
            String name = path.substring(0, path.lastIndexOf("."));

            // 后缀
            String suffix = path.substring(path.lastIndexOf(".") + 1);

            return StringUtils.equals("1", type) ? name : suffix;
        } else {
            return null;
        }
    }
}
