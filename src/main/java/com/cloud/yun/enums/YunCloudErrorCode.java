package com.cloud.yun.enums;

import java.text.MessageFormat;

/**
 * @ClassName YunCloudErrorCode
 * @Description
 * @Author jack
 * @Date 7/1/2022 4:08 PM
 * @Version 1.0
 **/
public enum YunCloudErrorCode implements YunCloudCode{
	TEST_ERR(500, 5001, "test err", "测试自定义异常"),
	ARG_ERR(500, 5002, "arg err {0},{1}", "测试自定义异常带参数{0},{1}"),
	;

	private final int value;
	private final int errCode;
	private final String enMessage;
	private final String zhMessage;

	YunCloudErrorCode(int value, int errCode, String enMessage, String zhMessage) {
		this.value = value;
		this.errCode = errCode;
		this.enMessage = enMessage;
		this.zhMessage = zhMessage;
	}

	@Override
	public int getHttpErrorCode() {
		return this.value;
	}

	@Override
	public String getMessage(Object... args) {
		return new MessageFormat(this.zhMessage).format(args);
	}
}
