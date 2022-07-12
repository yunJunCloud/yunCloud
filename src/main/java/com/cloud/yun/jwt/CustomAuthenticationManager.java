package com.cloud.yun.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @ClassName CustomAuthenticationManager
 * @Description CustomAuthenticationManager is to handle xxxx
 * @Author jack
 * @Date 7/6/2022 4:32 PM
 * @Version 1.0
 **/
@Component
public class CustomAuthenticationManager implements AuthenticationManager {
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		return null;
	}
}
