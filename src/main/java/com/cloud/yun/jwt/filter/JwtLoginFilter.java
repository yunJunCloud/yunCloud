package com.cloud.yun.jwt.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName JwtLoginFilter
 * @Description JwtLoginFilter is to handle xxxx
 * 集成 原security中登录的过滤器     创建认证的filter
 * @Author jack
 * @Date 7/4/2022 5:58 PM
 * @Version 1.0
 **/
//@Component
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
	private String usernameParameter = "username";
	private String passwordParameter = "password";

	//不重写AuthenticationManager 使用ProviderManager
	//private final AuthenticationManager authenticationManager;

	private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login", "POST");

	public JwtLoginFilter() {
		//this.authenticationManager = customAuthenticationManager;
		this.setPostOnly(false);
		this.setRequiresAuthenticationRequestMatcher(DEFAULT_ANT_PATH_REQUEST_MATCHER);
	}

	/***
	 * 从request中获取form表单中提交的用户登录信息
	 * @param request
	 * @param response
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
		//User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
		UsernamePasswordAuthenticationToken build = this.build(request);
		return getAuthenticationManager().authenticate(build);
	}

	private UsernamePasswordAuthenticationToken build(HttpServletRequest request){
		String userName= request.getParameter(usernameParameter);
		String passWord = request.getParameter(passwordParameter);
		//还应加入角色 TODO
		return  new UsernamePasswordAuthenticationToken(userName,passWord);
	}

//	@Override
//	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//		super.successfulAuthentication(request, response, chain, authResult);
//		Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
//		StringBuffer sb = new StringBuffer();
//		authorities.forEach(author -> sb.append(author.getAuthority()).append(","));
//		//header令牌类型type和所使用的的加密算法
//		Map<String,Object> header = new HashMap<>();
//		header.put("type", TokenType.TOKEN_JWT);
//		//是请求体和其它一些数据，例如包含了和用户相关的一些信息
//		Map<String,Object> playload = new HashMap<>();
//		playload.put("authorities",sb);
//		playload.put("sub",authResult.getName());
//		Date expireDate = new Date(new Date().getTime() + 1000 * JwtProperties.expire);
//		playload.put("exp",expireDate);
//		JWTUtil.createToken(header,playload, JWTSignerUtil.hs512(JwtProperties.secretKey.getBytes(StandardCharsets.UTF_8)));
//	}
//
//	@Override
//	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//		super.unsuccessfulAuthentication(request, response, failed);
//	}
}
