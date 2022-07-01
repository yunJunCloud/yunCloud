package com.cloud.yun.controller;

import cn.hutool.core.bean.BeanUtil;
import com.cloud.yun.base.BaseResult;
import com.cloud.yun.bean.User;
import com.cloud.yun.dto.UserDTO;
import com.cloud.yun.enums.YunCloudErrorCode;
import com.cloud.yun.exception.YunCloudException;
import com.cloud.yun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/test")
	public String testUser(){
		return "testMyk";
	}

	@PostMapping("/save")
	public BaseResult save(@RequestBody UserDTO userDTO){
		User user = BeanUtil.copyProperties(userDTO, User.class);
		//int insert = userService.insert(user);
		return new BaseResult().OK(userService.insert(user));

	}

	@GetMapping("/testException")
	public void testException(){
		if(1 !=2){
			throw new YunCloudException(YunCloudErrorCode.TEST_ERR,null);
			//throw new RuntimeException("1212");
		}
	}
}
