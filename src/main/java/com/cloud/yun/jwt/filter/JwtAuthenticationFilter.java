package com.cloud.yun.jwt.filter;

import cn.hutool.json.JSONUtil;
import com.cloud.yun.base.BaseResult;
import com.cloud.yun.exception.JwtException;
import com.cloud.yun.jwt.JwtConstants;
import com.cloud.yun.jwt.JwtTokenUtil;
import com.cloud.yun.jwt.TokenType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @ClassName JwtAuthenticationFilter
 * @Description JwtAuthenticationFilter is to handle xxxx
 * 授权认证的filter
 * @Author jack
 * @Date 7/11/2022 5:05 PM
 * @Version 1.0
 **/
@Slf4j
@Component
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		log.info("授权认证的开始==========="+ request.getRequestURI());
		if(request.getRequestURI().indexOf("index") != -1){
			chain.doFilter(request,response);
		}
		UsernamePasswordAuthenticationToken authentication = null;
		try {
			authentication = getAuthentication(request);
		}catch (Exception e) {
			//todo
		}
		 if(null != authentication){
			 SecurityContextHolder.getContext().setAuthentication(authentication);
		 }else {
			 response.setContentType(JwtConstants.contentType);
			 ServletOutputStream outputStream = response.getOutputStream();
			 BaseResult baseResult = new BaseResult().error("鉴权失败");
			 outputStream.write(JSONUtil.toJsonStr(baseResult).getBytes(StandardCharsets.UTF_8));
			 outputStream.flush();
			 outputStream.close();
		 }
		doFilter(request, response, chain);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) throws JwtException {
		// 获取Token字符串，token 置于 header 里
		String token = request.getHeader(TokenType.TOKEN_JWT);
		if (!StringUtils.hasText(token)) {
			token = request.getParameter(TokenType.TOKEN_JWT);
		}
		if (token != null && !"".equals(token.trim())) {
			// 从Token中解密获取用户名
			String userName = jwtTokenUtil.getUserName(token);
			boolean expired = jwtTokenUtil.isTokenExpired(token);
			if(expired){
				throw new JwtException("登录以过期");
			}

			if (userName != null) {
				// 从Token中解密获取用户角色
				String role = jwtTokenUtil.getUserRoleFromToken(token);
				// 将ROLE_XXX,ROLE_YYY格式的角色字符串转换为数组
				String[] roles = role.split(",");
				Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
				for (String s : roles) {
					authorities.add(new SimpleGrantedAuthority(s));
				}
				return new UsernamePasswordAuthenticationToken(userName, token, authorities);
			}
			return null;
		}
		return null;
	}
}
