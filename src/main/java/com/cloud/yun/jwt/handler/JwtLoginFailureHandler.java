package com.cloud.yun.jwt.handler;

import com.cloud.yun.base.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName JwtLoginFailureHandler
 * @Description JwtLoginFailureHandler is to handle xxxx   鉴权失败的处理器
 * @Author jack
 * @Date 7/11/2022 3:46 PM
 * @Version 1.0
 **/
@Slf4j
@Component
public class JwtLoginFailureHandler extends BaseHandler implements AuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		/***
		 * 1、 将错误信息放回
		 * 2、重定向到错误页面
		 */

		BaseResult err = new BaseResult<>().error(exception.getMessage());
		this.handleResult(request,response,err);
	}
}
