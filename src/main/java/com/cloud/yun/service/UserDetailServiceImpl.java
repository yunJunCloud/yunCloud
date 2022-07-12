package com.cloud.yun.service;

import com.cloud.yun.bean.AccountUser;
import com.cloud.yun.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName UserDetailServiceImpl
 * @Description UserDetailServiceImpl is to handle xxxx
 * @Author jack
 * @Date 7/11/2022 5:14 PM
 * @Version 1.0
 **/
@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailsService {
	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.finaUserByName(username);
		if(null == user){
			throw new UsernameNotFoundException("用户名或密码错误");
		}
		return new AccountUser(user.getId(),user.getName(), user.getPassword(),getUserAuthority(user.getId()));
	}

	/**
	 * 获取用户权限信息（角色、菜单权限）
	 * @param userId
	 * @return
	 */
	public List<GrantedAuthority> getUserAuthority(Long userId) {
		// 实际怎么写以数据表结构为准，这里只是写个例子
		// 角色(比如ROLE_admin)，菜单操作权限(比如sys:user:list)
		//String authority = sysUserService.getUserAuthorityInfo(userId);     // 比如ROLE_admin,ROLE_normal,sys:user:list,...
		String authority = "sys:user:list";
		return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
	}
}
