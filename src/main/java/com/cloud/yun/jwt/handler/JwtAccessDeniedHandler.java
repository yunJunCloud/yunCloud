package com.cloud.yun.jwt.handler;

import com.cloud.yun.base.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName JwtAccessDeniedHandler
 * @Description JwtAccessDeniedHandler is to handle xxxx  访问无权限的处理器
 * @Author jack
 * @Date 7/12/2022 10:59 AM
 * @Version 1.0
 **/
@Slf4j
@Component
public class JwtAccessDeniedHandler extends BaseHandler implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		BaseResult fail = new BaseResult().error(accessDeniedException.getMessage());
		log.error("access fail" + fail.getMessage());
		this.handleResult(request,response,fail);
	}
}
