package com.lyzyxy.attendance.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lyzyxy.attendance.base.Result;
import com.lyzyxy.attendance.model.Record;
import com.lyzyxy.attendance.model.Sign;
import com.lyzyxy.attendance.model.StudentCourse;
import com.lyzyxy.attendance.model.User;
import com.lyzyxy.attendance.service.IRecordService;
import com.lyzyxy.attendance.service.ISignService;
import com.lyzyxy.attendance.service.IUserService;
import com.lyzyxy.attendance.utils.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
    private IUserService userService;
	@Autowired
	private IRecordService recordService;
	@Autowired
	private ISignService signService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	//完成
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public Result login(String username, String password) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda()
				.eq(User::getUsername, username)
				.eq(User::getPassword,password);

		User user = userService.getOne(queryWrapper);

		logger.info("user:"+user);

		if (user != null) {
			return Result.success("success",user);
		} else {
			return Result.error("success",null);
		}

	}

	@RequestMapping("/register")
	public Result register(String name,String password,boolean isTeacher){
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda()
				.eq(User::getUsername, name);

		List<User> users = userService.list(queryWrapper);

		if(users.size() <= 0){
			User u = new User();
			u.setUsername(name);
			u.setPassword(password);
			u.setIsTeacher(isTeacher ? 1 : 0);

			if(userService.save(u))
				return Result.success(u);
			else
				return Result.error(u);
		}else{
			return  Result.error("用户名重复！");
		}
	}

	@PostMapping(value = "/uploading")
	public Result upload(@RequestParam("file") MultipartFile file){
		BASE64Encoder base64Encoder =new BASE64Encoder();
		String r = null;
		try {
			String base64EncoderImg = base64Encoder.encode(file.getBytes());
			r = FaceUtil.detect(base64EncoderImg,"BASE64");
		} catch (IOException e) {
			logger.error("upload error:" + e.getMessage());
		}
		//图片上传调用工具类

		return Result.success("chengg",r);
	}

	/**
	 * 图片上传
	 * @param file
	 * @return
	 */
	@PostMapping(value = "/upload")
	public Result uploadImg(@RequestParam("id") int id,@RequestParam("name") String name,
                            @RequestParam("file") MultipartFile file){
        String base64 = null;
        try {
            base64 = FileUtil.encodeBase64(file.getBytes());
        } catch (IOException e) {
			logger.error("file encode error:" + e.getMessage());
        }

		//图片上传调用工具类
		boolean r = false;
		try{
			//保存图片
			String path =  FileUtil.saveImage(file);

			if(path != null && !path.equals("")){
				if(FaceUtil.checkQuality(base64,"BASE64")){
					User user = userService.getById(id);

					FileUtil.deleteImage(user.getPhoto());

					user.setName(name);
					user.setPhoto(path);

					r = userService.updateById(user);
				}else{
					FileUtil.deleteImage(path);
					return Result.error("照片质量不合格，请重新上传");
				}
			}

			if(r) {
				return Result.success("上传照片成功", path);
			}else{
				FileUtil.deleteImage(path);
				return Result.error("上传照片失败","");
			}
		}catch (Exception e){
			logger.error("upload error:" + e.getMessage());
			return Result.error("上传照片失败","");
		}
	}

	@RequestMapping("/join")
	public Result joinCourse(int userId,int classId){
        StudentCourse sc = new StudentCourse();
        sc.setStudentId(userId);
        sc.setClassId(classId);

        StudentCourse qsc = sc.selectOne(
                new QueryWrapper<StudentCourse>()
                        .lambda()
                        .eq(StudentCourse::getStudentId, userId)
                        .eq(StudentCourse::getClassId,classId)
        );

        if(qsc == null){
        	User user = userService.getById(userId);
			String photo = user.getPhoto();

        	if(photo != null && !photo.equals("")){
        		String base64 = FileUtil.encodeBase64(photo);
        		if(base64 != null)
					FaceUtil.addOrUpdateUser(base64,"BASE64",""+classId,""+userId);
			}

            if(sc.insert()){
                return Result.success();
            }else{
                return Result.error();
            }
        }

        return Result.success();
	}

	/**
	 * 教师发起签到任务，需要手动关闭签到任务
	 * @param userId
	 * @param courseId
	 * @return
	 */
	public Result launch(int userId,int courseId){
		//TODO 根据userId检验用户是否为教师，以及是否为课程的任课老师

		Record record = new Record();
		record.setCourseId(courseId);
		record.setStart(new Date());

		if(record.insert()){
			return Result.success();
		}else{
			return Result.error();
		}
	}

	/**
	 * 检测是否可以签到
	 * @param userId
	 * @param courseId
	 * @return
	 */
	public Result ifSign(int userId,int courseId){
		QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
		queryWrapper
				.lambda()
				.eq(Record::getCourseId,courseId)
				.eq(Record::getEnd,null);

		List<Record> records = recordService.list(queryWrapper);

		if(records.size() == 1){
			return Result.success("success",records.get(0).getId());
		}else if(records.size() == 0){
			return Result.error("老师还没开启签到！");
		}else {
			return Result.error("老师还有没结束的签到！");
		}
	}

	/**
	 * 学生签到
	 * @param studentId
	 * @param courseId
	 * @param recordId
	 * @param distance
	 * @param file
	 * @return
	 */
	public Result sign(@RequestParam("studentId") int studentId,
					   @RequestParam("courseId") int courseId,
					   @RequestParam("recordId") int recordId,
					   @RequestParam("distance") int distance,
					   @RequestParam("file") MultipartFile file){

		//检测是否已经签过到
		QueryWrapper<Sign> queryWrapper = new QueryWrapper<>();
		queryWrapper
				.lambda()
				.eq(Sign::getRecordId,recordId)
				.eq(Sign::getStudentId,studentId);

		int count = signService.count(queryWrapper);

		if(count >= 1)
			return Result.error("已经签到！");

		String base64 = null;
		try {
			base64 = FileUtil.encodeBase64(file.getBytes());
		} catch (IOException e) {
			logger.error("file encode error:" + e.getMessage());
		}

		//在照片库中识别上传的人脸，只返回相似度大于70%的结果
		JSONObject result = FaceUtil.search(base64,"BASE64",""+courseId);

		if(result != null){
			int user_id = Integer.parseInt(result.getString("user_id"));
			double score = result.getDouble("score");

			//识别结果和学生是同一人的，保存签到
			if(user_id == studentId){
				Sign sign = new Sign();
				sign.setStudentId(user_id);
				sign.setRecordId(recordId);
				sign.setRate(score);
				sign.setDistance(distance);
				sign.setTime(new Date());

				if(userService.sign(sign)){
					return Result.success("签到成功！");
				}
			}
		}

		return Result.error("签到失败！");
	}
}
