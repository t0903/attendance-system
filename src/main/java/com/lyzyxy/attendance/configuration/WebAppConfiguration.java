package com.lyzyxy.attendance.configuration;

import com.lyzyxy.attendance.utils.ConstantKit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author cailei.lu
 * @description
 * @date 2018/8/3
 */
@Configuration
public class WebAppConfiguration implements WebMvcConfigurer {
    /**
     * 配置的图片映射
     */
    private static final String imgPath = "file:" + ConstantKit.UPLOAD_PATH + ConstantKit.IMG_FILE_NAME +  "/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有访问img/virtual/**的请求映射到文件上传的路径下 C:\Users\wanghao/upload/img（图片的保存路径）
        String pathPatterns = "/" + ConstantKit.VIRTUAL_IMG_PATH + "/**";
        registry.addResourceHandler(pathPatterns).addResourceLocations(imgPath);
    }
}
