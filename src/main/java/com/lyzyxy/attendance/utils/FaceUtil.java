package com.lyzyxy.attendance.utils;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.FaceVerifyRequest;
import com.lyzyxy.attendance.controller.UserController;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class FaceUtil {
    public static final String APP_ID = "16123795";
    public static final String API_KEY = "R9xy8w2L0VWkptYGqOVRQWET";
    public static final String SECRET_KEY = "NOQWyKf64FCZycrhz98TWh98Pxbx0nEV";

    private static final Logger logger = LoggerFactory.getLogger(FaceUtil.class);

    public static final AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

    public static void main(String[] args){
        String url = "http://cdn.ucshare.com.cn/a39ee1a6bf324178b9faab5f7a002a7e.jpg";
        String url2 = "http://cdn.ucshare.com.cn/timg%20%288%29.jpg";
        String url3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1564070582597&di=fe6bc5bc6a77956208b4e415aeed52fd&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20110524%2FImg308377006.jpg";
        FaceUtil.search(url3,"URL","1");
    }

    /**
     * 图片活体检测
     * @param image
     * @param imageType
     */
    public static void antiSpoofing(String image,String imageType) {
        FaceVerifyRequest req = new FaceVerifyRequest(image, imageType);
        ArrayList<FaceVerifyRequest> list = new ArrayList<FaceVerifyRequest>();
        list.add(req);
        JSONObject res = client.faceverify(list);
        System.out.println(res.toString(2));
    }

    public static JSONObject search(String image,String imageType,String groupId){
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("quality_control", "NORMAL");
        options.put("max_face_num", "1");
        options.put("match_threshold", "70");
        options.put("liveness_control", "LOW");

        JSONObject res = client.search(image, imageType, groupId, options);

        //System.out.println(res.toString(2));

        if(res.getInt("error_code") == 0) {
            res = res.getJSONObject("result");
            return res.getJSONArray("user_list").getJSONObject(0);
        }else {
            logger.error("FaceUtil search error:"+res.toString(2));
            return null;
        }
    }

    /**
     * 检测人脸照片的质量
     * @param image
     * @param imageType
     * @return
     */
    public static boolean checkQuality(String image,String imageType) {
        /*
        {
          "result": {
            "face_num": 1,
            "face_list": [{
              "face_type": { human: 真实人脸 cartoon: 卡通人脸
                "probability": 0.99,
                "type": "human"
              },
              "liveness": {"livemapscore": 0.98}, 活体检测结果
              "angle": {人 脸旋转角度参数
                "roll": -0.49,
                "pitch": 7.84,
                "yaw": 2.07
              },
              "face_token": "dc49e921029353514772fe7f8d7d441f",
              "location": {
                "top": 191.81,
                "left": 84.5,
                "rotation": 1,
                "width": 221,
                "height": 200
              },
              "face_probability": 1,
              "quality": {人脸质量信息
                "illumination": 205,取值范围在[0~255], 表示脸部区域的光照程度 越大表示光照越好
                "occlusion": {人脸各部分遮挡的概率，范围[0~1]，0表示完整，1表示不完整
                  "right_eye": 0,右眼遮挡比例
                  "nose": 0,
                  "mouth": 0,
                  "left_eye": 0,左眼遮挡比例
                  "left_cheek": 0,左脸颊遮挡比例
                  "chin_contour": 0,
                  "right_cheek": 0
                },
                "blur": 0,人脸模糊程度，范围[0~1]，0表示清晰，1表示模糊
                "completeness": 1 人脸完整度，0或1, 0为人脸溢出图像边界，1为人脸都在图像边界内
              }
            }]
          },
          "log_id": 305486807746748691,
          "error_msg": "SUCCESS",
          "cached": 0,
          "error_code": 0,
          "timestamp": 1560774674
        }
         */
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("face_field", "quality,face_type");
        options.put("max_face_num", "1");
        options.put("face_type", "LIVE");
        options.put("liveness_control", "NORMAL");

        // 人脸检测
        JSONObject res = client.detect(image, imageType, options);

        if(res.getInt("error_code") == 0) {

            res = res.getJSONObject("result");

            //System.out.println(res.toString(2));

            if (res.getInt("face_num") == 1) {
                JSONObject jsonObject = (JSONObject) res.getJSONArray("face_list").get(0);
                JSONObject quality = jsonObject.getJSONObject("quality");
                JSONObject occlusion = quality.getJSONObject("occlusion");
                int illumination = quality.getInt("illumination"); //40
                double blur = quality.getDouble("blur");//小于0.7
            /*
            left_eye : 0.6, #左眼被遮挡的阈值
            right_eye : 0.6, #右眼被遮挡的阈值
            nose : 0.7, #鼻子被遮挡的阈值
            mouth : 0.7, #嘴巴被遮挡的阈值
            left_check : 0.8, #左脸颊被遮挡的阈值
            right_check : 0.8, #右脸颊被遮挡的阈值
            chin_contour : 0.6, #下巴被遮挡阈值
             */
                double left_eye = occlusion.getDouble("left_eye");
                double right_eye = occlusion.getDouble("right_eye");
                double nose = occlusion.getDouble("nose");
                double mouth = occlusion.getDouble("mouth");
                double left_cheek = occlusion.getDouble("left_cheek");
                double right_cheek = occlusion.getDouble("right_cheek");
                double chin_contour = occlusion.getDouble("chin_contour");

                if (illumination > 40 && blur < 0.7 && left_eye < 0.6 && right_eye < 0.6
                        && nose < 0.7 && mouth < 0.7 && left_cheek < 0.8 && right_cheek < 0.8
                        && chin_contour < 0.6) {
                    return true;
                } else {
                    return false;
                }


            } else {
                return false;
            }
        }
        return false;
    }

    public static String detect(String image,String imageType) {
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("face_field", "gender");
        options.put("max_face_num", "3");
        options.put("face_type", "LIVE");
        // 人脸检测
        JSONObject res = client.detect(image, imageType, options);
        return res.toString(2);
    }

    /**
     *
     * @param image
     * @param imageType
     * @param groupId
     * @param userId
     * @return faceToken
     */
    public static String addOrUpdateUser(String image,String imageType,String groupId,String userId) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
        options.put("action_type", "REPLACE");

        // 人脸注册
        JSONObject res = client.addUser(image, imageType, groupId, userId, options).getJSONObject("result");;

        return res.getString("face_token");
    }
}
