package com.cloud.yun.jwt;

import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTHeader;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.cloud.yun.service.UserService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @ClassName JwtTokenProvider
 * @Description JwtTokenProvider is to handle xxxx
 * @Author jack
 * @Date 7/6/2022 4:41 PM
 * @Version 1.0
 **/
@Component
public class JwtTokenProvider {
	@Autowired
	private JwtProperties jwtProperties;

	@Autowired
	private UserService userService;

	private String secretKey = "123";

	private final String ROLE_KEY = "authorities";

	private final String SUB_KEY = "sub";

	private final String EXP_KEY = "exp";

	@PostConstruct
	protected void init(){
		secretKey = Base64.getEncoder().encodeToString(jwtProperties.getPrivateKey().getBytes(StandardCharsets.UTF_8));
	}

	/**
	 *
	 * @param userName 用户名
	 * @param authorities 类似角色
	 * @return
	 */
	public String createToken(String userName, Collection<? extends GrantedAuthority> authorities){
		StringBuffer sb = new StringBuffer();
		if(null != authorities){
			authorities.forEach(author -> sb.append(author.getAuthority()).append(","));
		}
		//header令牌类型type和所使用的的加密算法
		Map<String,Object> header = new HashMap<>();
		header.put("type", TokenType.TOKEN_JWT);
		//是请求体和其它一些数据，例如包含了和用户相关的一些信息
		Map<String,Object> playload = new HashMap<>();
		playload.put(ROLE_KEY,sb);
		playload.put(SUB_KEY,userName);
		Date expireDate = new Date(new Date().getTime() + 1000 * JwtProperties.expire);
		playload.put(EXP_KEY,expireDate);
		//生成签名key
		JWTSigner jwtSigner = JWTSignerUtil.hs256(secretKey.getBytes(StandardCharsets.UTF_8));
		return JWTUtil.createToken(header, playload, jwtSigner);
	}

	/**
	 * 获取payload中的信息 包括用户   角色  过期时间等信息
	 * @param token
	 * @return
	 */
	public JWTPayload getPayload(String token){
		JWT jwt = JWTUtil.parseToken(token);
		return jwt.getPayload();
	}

	public Object getPayloadClaimByName(String token, String key){
		JWTPayload payload = this.getPayload(token);
		return payload.getClaim(key);
	}

	public String getUserName(String token){
		return  (String) this.getPayloadClaimByName(token, SUB_KEY);
	}

	public String getUserRoleFromToken(String token){
		return (String)this.getPayloadClaimByName(token,ROLE_KEY);
	}

	/**
	 * token 是否已过期
	 * @param token
	 * @return
	 */
	public boolean isTokenExpired(String token){
		Integer exp = (Integer) this.getPayloadClaimByName(token, "exp");
		//Integer expLong = Integer.parseInt(exp);
		Date date = new Date();
		date.setTime(exp.intValue());
		return date.before(new Date());
	}

	public static void main(String[] args) {
		JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
		String zhangsan = jwtTokenProvider.createToken("zhangsan", null);
		System.out.println(zhangsan);
		boolean tokenExpired = jwtTokenProvider.isTokenExpired(zhangsan);
		System.out.println(tokenExpired);
	}
}
