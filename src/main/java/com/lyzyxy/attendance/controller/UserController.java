package com.lyzyxy.attendance.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyzyxy.attendance.base.Result;
import com.lyzyxy.attendance.model.StudentCourse;
import com.lyzyxy.attendance.model.User;
import com.lyzyxy.attendance.service.IUserService;
import com.lyzyxy.attendance.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
    private IUserService userService;
	
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
			e.printStackTrace();
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

        BASE64Encoder base64Encoder = new BASE64Encoder();
        String base64 = null;
        try {
            base64 = base64Encoder.encode(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
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
}
