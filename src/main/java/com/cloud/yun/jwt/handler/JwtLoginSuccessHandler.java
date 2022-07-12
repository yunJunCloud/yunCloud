package com.cloud.yun.jwt.handler;

import com.cloud.yun.base.BaseResult;
import com.cloud.yun.jwt.JwtTokenProvider;
import com.cloud.yun.jwt.TokenType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName JwtLoginSuccessHandler
 * @Description JwtLoginSuccessHandler is to handle  处理鉴权成功后的处理类
 * @Author jack
 * @Date 7/11/2022 3:24 PM
 * @Version 1.0
 **/
@Component
@Slf4j
public class JwtLoginSuccessHandler extends BaseHandler implements AuthenticationSuccessHandler {

	@Resource
	private JwtTokenProvider jwtTokenProvider;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		//生成jwtToken 并放置到请求头中
		String token = jwtTokenProvider.createToken(authentication.getName(), authentication.getAuthorities());
		response.setHeader(TokenType.TOKEN_JWT,token);
		log.info("-----login success---- jwtToken : " + token);
		/**
		 * 登录成功之后，有多种处理方式 1、返回json，告知前端登录成功，自己重定向
		 * 2、 后端直接重定向到指定的页面
		 */
		BaseResult ok = new BaseResult<>().OK();
		this.handleResult(request,response,ok);
	}
}
