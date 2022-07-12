package com.cloud.yun.service;

import com.cloud.yun.base.BaseService;
import com.cloud.yun.bean.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author jack
 * @Date 6/23/2022 6:31 PM
 * @Version 1.0
 **/
public interface UserService extends BaseService<User>{
	User finaUserByName(String username) throws UsernameNotFoundException;
}
