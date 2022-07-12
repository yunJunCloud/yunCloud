package com.cloud.yun.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cloud.yun.base.BaseModel;
import lombok.Data;

/**
 * @ClassName DicCode
 * @Description DicCode is to handle xxxx
 * @Author jack
 * @Date 7/6/2022 2:46 PM
 * @Version 1.0
 **/
@Data
public class DicCode extends BaseModel {
	@TableId(type = IdType.AUTO)
	private int id;

	/**
	 * 码值
	 */
	private int code;

	/**
	 * 码值对应的父id
	 */
	private int parentId;

	/**
	 * 码值值
	 */
	private String value;

	/**
	 * 描述
	 */
	private String desc;
}
