package com.lyzyxy.attendance.utils;


public final class ConstantKit {

    /**
     * 设置删除标志为真
     */
    public static final Integer DEL_FLAG_TRUE = 1;

    /**
     * 设置删除标志为假
     */
    public static final Integer DEL_FLAG_FALSE = 0;

    /**
     * redis存储token设置的过期时间，10分钟
     */
    public static final Integer TOKEN_EXPIRE_TIME = 60 * 10;

    /**
     * 设置可以重置token过期时间的时间界限
     */
    public static final Integer TOKEN_RESET_TIME = 1000 * 100;

    /** 文件上传/图片   根目录 */
    public static final String UPLOAD_PATH = System.getProperty("user.home") + "/upload/";

    /** 图片目录 */
    public static final String IMG_FILE_NAME = "images";

    /** 图片相对路径 */
    public static final String VIRTUAL_IMG_PATH = "images";


}
