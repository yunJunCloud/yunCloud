package com.cloud.yun.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.yun.base.BaseServiceImpl;
import com.cloud.yun.bean.User;
import com.cloud.yun.constants.UserFieldConstants;
import com.cloud.yun.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author jack
 * @Date 6/23/2022 6:32 PM
 * @Version 1.0
 **/
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService{

	@Resource
	private UserMapper userMapper;


	@Override
	public User finaUserByName(String username) throws UsernameNotFoundException {
		return userMapper.selectOne(new QueryWrapper<User>().eq(UserFieldConstants.USER_NAME, username));
	}
}
