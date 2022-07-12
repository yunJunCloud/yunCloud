package com.cloud.yun.bean;

import com.cloud.yun.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName Permission
 * @Description Permission is to handle xxxx
 * @Author jack
 * @Date 7/12/2022 9:52 AM
 * @Version 1.0
 **/
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Permission extends BaseModel implements Serializable {
	private static final long serialVersionUID = 8400414916933801036L;
	private String id;

	/**
	 * 用户权限
	 */
	private String code;

	/**
	 * 权限描述
	 */
	private String url;

	/**
	 * 资源url
	 */
	private String desc;
}
