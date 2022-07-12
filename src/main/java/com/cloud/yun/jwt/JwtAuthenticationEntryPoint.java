package com.cloud.yun.jwt;

import com.cloud.yun.base.BaseResult;
import com.cloud.yun.jwt.handler.BaseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName JwtAuthenticationEntryPoint
 * @Description JwtAuthenticationEntryPoint is to handle xxxx
 * @Author jack
 * @Date 7/12/2022 5:12 PM
 * @Version 1.0
 **/
@Slf4j
@Component
public class JwtAuthenticationEntryPoint extends BaseHandler implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		log.info("------未授权----");
		BaseResult err = new BaseResult<>().error(authException.getMessage());
		this.handleResult(request,response,err);
	}
}
