package com.cloud.yun.bean;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName Payload
 * @Description Payload is to handle xxxx
 * @Author jack
 * @Date 7/4/2022 5:49 PM
 * @Version 1.0
 **/
@Data
public class Payload <T>{
	private String id;
	private T userInfo;
	private Date expiration;
}
