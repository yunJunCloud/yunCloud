package com.cloud.yun.config;

import com.cloud.yun.constants.URLConstants;
import com.cloud.yun.jwt.JwtAuthenticationEntryPoint;
import com.cloud.yun.jwt.PasswordEncoder;
import com.cloud.yun.jwt.filter.JwtAuthenticationFilter;
import com.cloud.yun.jwt.handler.JwtAccessDeniedHandler;
import com.cloud.yun.jwt.handler.JwtLoginFailureHandler;
import com.cloud.yun.jwt.handler.JwtLoginSuccessHandler;
import com.cloud.yun.jwt.handler.JwtLogoutSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @ClassName SecurityConfig
 * @Description SecurityConfig is to handle xxxx
 * @Author jack
 * @Date 7/12/2022 5:02 PM
 * @Version 1.0
 **/
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtLogoutSuccessHandler jwtLogoutSuccessHandler;

	@Autowired
	private JwtLoginSuccessHandler jwtLoginSuccessHandler;

	@Autowired
	private JwtLoginFailureHandler jwtLoginFailureHandler;

	@Autowired
	private JwtAccessDeniedHandler jwtAccessDeniedHandler;

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Bean
	JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager());
		return jwtAuthenticationFilter;
	}

	public static final String[] URL_WHITELIST = {
			URLConstants.login,
			URLConstants.common+URLConstants.common_captcha
	};

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	PasswordEncoder PasswordEncoder() {
		return new PasswordEncoder();
	}

	/**
	 * anyRequest          |   匹配所有请求路径
	 * access              |   SpringEl表达式结果为true时可以访问
	 * anonymous           |   匿名可以访问
	 * denyAll             |   用户不能访问
	 * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
	 * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
	 * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
	 * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
	 * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
	 * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
	 * permitAll           |   用户可以任意访问
	 * rememberMe          |   允许通过remember-me登录的用户访问
	 * authenticated       |   用户登录后可访问
	 */

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
				.loginPage("/")
				.loginProcessingUrl(URL_WHITELIST[0])
				.successHandler(jwtLoginSuccessHandler)
				.failureHandler(jwtLoginFailureHandler)

				.and()
				.logout()
				.logoutSuccessHandler(jwtLogoutSuccessHandler)

				.and()
				.csrf()
				.disable()
				// 禁用session
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				.and()
				.exceptionHandling()
				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.accessDeniedHandler(jwtAccessDeniedHandler)

				.and()
				.authorizeRequests()
				.antMatchers(URL_WHITELIST)
				.permitAll()
				// 除上面外的所有请求全部需要鉴权认证
				.anyRequest().authenticated()
				.and()
				.addFilter(jwtAuthenticationFilter())
				;

	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		//放行登录接口 user/login   anonymous()只能未登录的状态访问
//		http.authorizeRequests()
//				.antMatchers(URLConstants.login).anonymous()
//				//permitAll() 所有人能访问（登录和未登录）
//				.antMatchers("/index")
//				.permitAll()
//				//除上述外所有请求都要认证
//				.and().authorizeRequests();
//
//		http.cors().and().csrf().disable()
//				.formLogin()
//				.loginPage(URLConstants.login)
//				.successHandler(jwtLoginSuccessHandler)
//				.failureHandler(jwtLoginFailureHandler)
//
//				.and()
//				.logout()
//				.logoutSuccessHandler(jwtLogoutSuccessHandler)
//
//				// 禁用session
//                .and()
//				.sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//				// 配置拦截规则
//				.and()
//				.authorizeRequests()
//				.antMatchers(URL_WHITELIST).permitAll()
//				.anyRequest().authenticated()
//				// 异常处理器
//				.and()
//				.exceptionHandling()
//				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
//				.accessDeniedHandler(jwtAccessDeniedHandler)
//				// 配置自定义的过滤器
//				//.and()
//				//.addFilter(jwtAuthenticationFilter())
//				// 验证码过滤器放在UsernamePassword过滤器之前
//				//.addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
//		;
//
//	}
}
