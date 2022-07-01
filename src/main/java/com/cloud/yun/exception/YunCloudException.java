package com.cloud.yun.exception;

import com.cloud.yun.enums.YunCloudCode;

/**
 * @ClassName YunCloudException
 * @Description 自定义异常类
 * @Author jack
 * @Date 7/1/2022 4:00 PM
 * @Version 1.0
 **/
public class YunCloudException extends RuntimeException {

	private  YunCloudCode yunCloudCode ; //枚举异常（来自 HttpStatusCode 或 YunCloudErrorCode）
	private  Object[] args;

	public YunCloudException(YunCloudCode yunCloudCode, Object... args) {
		super(yunCloudCode.getMessage(args));
		this.yunCloudCode = yunCloudCode;
		this.args = args;
	}

	public YunCloudException(YunCloudCode yunCloudCode) {
		this.yunCloudCode = yunCloudCode;
	}

	public Integer getHttpStatusCode(){
		return this.yunCloudCode == null ? 500 : this.yunCloudCode.getHttpErrorCode();
	}

	@Override
	public String getMessage(){
		return this.yunCloudCode == null ? super.getMessage() : this.yunCloudCode.getMessage(args);
	}
}
