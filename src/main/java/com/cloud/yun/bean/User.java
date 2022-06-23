package com.cloud.yun.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cloud.yun.base.BaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName User
 * @Description TODO
 * @Author jack
 * @Date 6/23/2022 5:52 PM
 * @Version 1.0
 **/
@Getter
@Setter
@ToString
public class User extends BaseModel implements Serializable {
	private static final long serialVersionUID = 8295050210277575080L;

	@TableId(type = IdType.AUTO)
	private Long id;

	private String name;

	private Integer age;

}
