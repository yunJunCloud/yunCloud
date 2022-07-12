package com.cloud.yun.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;

/**
 * @ClassName CommonUtils
 * @Description CommonUtils is to handle   基础工具包
 * @Author jack
 * @Date 7/12/2022 1:40 PM
 * @Version 1.0
 **/
public class CommonUtils {
	public static AES buildAes(){
		byte[] encoded = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
		return SecureUtil.aes(encoded);
	}
}
