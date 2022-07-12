package com.cloud.yun.exception;

import javax.naming.AuthenticationException;

/**
 * @ClassName JwtException
 * @Description JwtException is to handle xxxx
 * @Author jack
 * @Date 7/12/2022 4:53 PM
 * @Version 1.0
 **/
public class JwtException extends AuthenticationException {
	public JwtException(String msg) {
		super(msg);
	}
}
