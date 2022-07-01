package com.cloud.yun.enums;

import cn.hutool.core.collection.CollUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName YunCloudCode
 * @Description codeInterface
 * @Author jack
 * @Date 7/1/2022 4:06 PM
 * @Version 1.0
 **/
public interface YunCloudCode {
	int getHttpErrorCode();
	String getMessage(Object... args);
}
