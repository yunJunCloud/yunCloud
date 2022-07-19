package com.cloud.yun.jwt;

import cn.hutool.core.util.CharsetUtil;
import com.cloud.yun.utils.CommonUtils;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * @ClassName PasswordEncoder
 * @Description PasswordEncoder is to handle xxxx    自定义password的比较方法   实现PasswordEncoder的matches方法    DaoAuthenticationProvider
 * @Author jack
 * @Date 7/12/2022 11:40 AM
 * @Version 1.0
 **/
@NoArgsConstructor
public class PasswordEncoder extends BCryptPasswordEncoder {
	/***
	 * SpringSecurity提供了用于密码加密解密的工具类BCryptPasswordEncoder  因为security提供的该类并没有考虑前端加密的问题。我们需要重写其matches方法，该方法用于判断从前端接收的密码与数据库中的密码是否一致
	 * @param rawPassword
	 * @param encodedPassword
	 * @return
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		String password = rawPassword.toString();
		password = CommonUtils.buildAes().decryptStr(password, CharsetUtil.CHARSET_UTF_8);
		if(null != encodedPassword && encodedPassword.length() !=0){
			return BCrypt.checkpw(password,encodedPassword);
		}else {
			return  false;
		}

	}
}
