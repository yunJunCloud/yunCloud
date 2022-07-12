package com.cloud.yun.jwt.handler;

import cn.hutool.json.JSONUtil;
import com.cloud.yun.base.BaseResult;
import com.cloud.yun.jwt.JwtConstants;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName BaseHandler
 * @Description BaseHandler is to handle xxxx
 * @Author jack
 * @Date 7/12/2022 11:03 AM
 * @Version 1.0
 **/
public class BaseHandler {
	public void handleResult(HttpServletRequest request, HttpServletResponse response, BaseResult baseResult) throws IOException {
		response.setContentType(JwtConstants.contentType);
		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.write(JSONUtil.toJsonStr(baseResult).getBytes(StandardCharsets.UTF_8));
		outputStream.flush();
		outputStream.close();
	}
}
