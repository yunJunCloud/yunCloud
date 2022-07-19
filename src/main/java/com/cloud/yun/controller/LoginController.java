package com.cloud.yun.controller;

import cn.hutool.core.bean.BeanUtil;
import com.cloud.yun.base.BaseResult;
import com.cloud.yun.bean.User;
import com.cloud.yun.constants.URLConstants;
import com.cloud.yun.dto.UserDTO;
import com.cloud.yun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName LoginController
 * @Description LoginController is to handle xxxx
 * @Author jack
 * @Date 7/19/2022 3:50 PM
 * @Version 1.0
 **/
@RestController
@RequestMapping(URLConstants.login)
public class LoginController {

	@Autowired
	private UserService userService;

	@PostMapping("")
	public BaseResult save(@RequestBody UserDTO userDTO){
		if(!StringUtils.isEmpty(userDTO.getNickName())){
			User user = userService.finaUserByName(userDTO.getNickName());
			if(null!=user){
				String password = user.getPassword();
				if(password.equals(userDTO.getPassword())){
					return new BaseResult().OK();
				}
			}
		}
		return new BaseResult().error("login fail");

	}
}
