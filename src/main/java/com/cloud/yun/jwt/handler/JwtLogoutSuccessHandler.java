package com.cloud.yun.jwt.handler;

import com.cloud.yun.base.BaseResult;
import com.cloud.yun.jwt.TokenType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName JwtLogoutSuccessHandler
 * @Description JwtLogoutSuccessHandler is to handle xxxx
 * @Author jack
 * @Date 7/12/2022 11:11 AM
 * @Version 1.0
 **/
@Slf4j
@Component
public class JwtLogoutSuccessHandler extends BaseHandler implements LogoutSuccessHandler {
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		if (null != authentication) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}
		/**
		 * jwt是无状态的，无法做到销毁jwt ，jwt生成之后只能等到过期之后，才会失效，
		 * 此处采用将请求头中的jwt置空的方式来清除浏览器中保存的jwt
		 * 同时还要讲 上下文的 securityContext中保存的用户信息清除
		 */
		response.setHeader(TokenType.TOKEN_JWT, "");
		BaseResult ok = new BaseResult<>().OK();
		ok.setMessage("退出成功");
		this.handleResult(request,response,ok);
	}
}
