package com.cloud.yun.base;

import com.cloud.yun.enums.HttpStatusCode;
import com.cloud.yun.exception.YunCloudException;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName BaseResult
 * @Description 结果返回类
 * @Author jack
 * @Date 6/30/2022 6:25 PM
 * @Version 1.0
 **/
@Data
public class BaseResult <T> implements Serializable {
	private static final long serialVersionUID = -5861300320082601412L;
	private T data;
	private int code;
	private String clazz;
	private String method;
	private int lineNumber;
	private String message;

	public BaseResult(){};

	public BaseResult OK(T data){
		this.setCode(HttpStatusCode.OK.Value());
		this.setData(data);
		this.setMessage(HttpStatusCode.OK.ZhMessage());
		return this;
	}

	public BaseResult OK(){
		this.setCode(HttpStatusCode.OK.Value());
		//this.setData(data);
		this.setMessage(HttpStatusCode.OK.ZhMessage());
		return this;
	}

	public void error() {
		try {
			StackTraceElement ste = (new Throwable()).getStackTrace()[1];
			this.clazz = ste.getClassName();
			this.method = ste.getMethodName();
			this.lineNumber = ste.getLineNumber();
		} catch (Exception var3) {
		}
	}

	public BaseResult error(String message) {
		try {
			error();
			this.message = message;
		} catch (Exception var3) {
		}
		return this;
	}

	public BaseResult error(int code, String message) {
		try {
			error();
			this.code = code;
			this.message = message;
		} catch (Exception var3) {
		}
		return this;
	}

	public BaseResult error(YunCloudException e) {
		try {
			error();
			this.code = e.getHttpStatusCode();
			this.message = e.getMessage();
		} catch (Exception var3) {
		}
		return this;
	}

}
