package com.cloud.yun.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName JwtProperties
 * @Description JwtProperties is to handle xxxx
 * @Author jack
 * @Date 7/6/2022 4:41 PM
 * @Version 1.0
 **/
@Configuration
@ConfigurationProperties(prefix = "yuncloud.jwt")
@Data
public class JwtProperties {
	/**
	 * 公钥
	 */
	private String publicKey;

	/**
	 * 私钥
	 */
	private String privateKey;

	/**
	 * session过期时间（分钟），注意由于程序中允许服务端和客户端时钟相差3分钟，所以实际的过期时间会在设置的基础上+3
	 */
	public static long expire = 5;

}
