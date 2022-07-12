package com.cloud.yun.config;

import com.cloud.yun.jwt.JwtAuthenticationEntryPoint;
import com.cloud.yun.jwt.PasswordEncoder;
import com.cloud.yun.jwt.handler.JwtAccessDeniedHandler;
import com.cloud.yun.jwt.handler.JwtLoginFailureHandler;
import com.cloud.yun.jwt.handler.JwtLoginSuccessHandler;
import com.cloud.yun.jwt.handler.JwtLogoutSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

	private static final String[] URL_WHITELIST = {
			"/login",
			"/logout",
			"/captcha",
			"/favicon.ico"
	};

	@Bean
	PasswordEncoder PasswordEncoder() {
		return new PasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//放行登录接口 user/login   anonymous()只能未登录的状态访问
		http.authorizeRequests()
				.antMatchers("/login").anonymous()
				//permitAll() 所有人能访问（登录和未登录）
				.antMatchers("/index").permitAll()
				//除上述外所有请求都要认证
				.and().authorizeRequests();

		http.cors().and().csrf().disable()
				.formLogin()
				.successHandler(jwtLoginSuccessHandler)
				.failureHandler(jwtLoginFailureHandler)

				.and()
				.logout()
				.logoutSuccessHandler(jwtLogoutSuccessHandler)

				// 禁用session
                .and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				// 配置拦截规则
				.and()
				.authorizeRequests()
				.antMatchers(URL_WHITELIST).permitAll()
				.anyRequest().authenticated()
				// 异常处理器
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.accessDeniedHandler(jwtAccessDeniedHandler)
				// 配置自定义的过滤器
				//.and()
				//.addFilter(jwtAuthenticationFilter())
				// 验证码过滤器放在UsernamePassword过滤器之前
				//.addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
		;

	}
}
