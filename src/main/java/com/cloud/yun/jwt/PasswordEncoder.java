package com.cloud.yun.jwt;

import cn.hutool.core.util.CharsetUtil;
import com.cloud.yun.utils.CommonUtils;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * @ClassName PasswordEncoder
 * @Description PasswordEncoder is to handle xxxx
 * @Author jack
 * @Date 7/12/2022 11:40 AM
 * @Version 1.0
 **/
@NoArgsConstructor
public class PasswordEncoder extends BCryptPasswordEncoder {
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
