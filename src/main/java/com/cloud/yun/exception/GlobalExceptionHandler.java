package com.cloud.yun.exception;

import cn.hutool.http.server.HttpServerRequest;
import com.cloud.yun.base.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName GlobalExceptionHandler
 * @Description  全局异常处理类
 * @Author jack
 * @Date 6/30/2022 8:00 PM
 * @Version 1.0
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	/**
	 * 处理自定义异常
	 * @param req
	 * @param yunCloudException
	 * @return
	 */
//	@ExceptionHandler(value = YunCloudException.class)
//	@ResponseBody
//	public BaseResult yunCloudExceptionHandle(HttpServerRequest req, YunCloudException yunCloudException){
//		log.error("自定义异常----"+yunCloudException.getMessage());
//		return new BaseResult().error(yunCloudException);
//	}
	//加入HttpServerRequest 后之后 会导致   java.lang.IllegalStateException: Could not resolve parameter [0] in public com.xx.xx com.xx.xx.handler.GlobalExceptionHandler.error，No suitable resolver
	//出现的原因 ，参数问题

	@ExceptionHandler(value = YunCloudException.class)
	@ResponseBody
	public BaseResult yunCloudExceptionHandle(YunCloudException yunCloudException){
		log.error("自定义异常----"+yunCloudException.getMessage());
		return new BaseResult().error(yunCloudException);
	}

	/***
	 * 运行行异常的处理
	 * @param req
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public BaseResult  baseException(Exception e){
		log.error("Exceptiony----" + e.getMessage());
		return new BaseResult().error(e.getMessage());
	}
}
