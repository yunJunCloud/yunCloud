package com.cloud.yun.controller;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.lang.UUID;
import com.cloud.yun.base.BaseResult;
import com.cloud.yun.constants.CommonConstants;
import com.cloud.yun.constants.URLConstants;
import com.cloud.yun.utils.RedisUtils;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @ClassName CommonController
 * @Description CommonController is to handle xxxx
 * @Author jack
 * @Date 7/11/2022 4:01 PM
 * @Version 1.0
 **/
@RestController
@RequestMapping(URLConstants.common)
public class CommonController {

	@Autowired
	private Producer producer;

	@Autowired
	private RedisUtils redisUtils;

	@GetMapping(URLConstants.common_captcha)
	public BaseResult<String> buildCaptcha() throws IOException {
		String key = UUID.randomUUID().toString();
		String code = producer.createText();
		BufferedImage image = producer.createImage(code);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(image,"jpg",outputStream);
		String str = "data:image/jpeg;base64,";
		String base64Img = str + Base64Encoder.encode(outputStream.toByteArray());

		redisUtils.hmSet(CommonConstants.CAPTCHA_KEY, key, code,120L);

		return new BaseResult<String>().OK(base64Img);

	}
}
