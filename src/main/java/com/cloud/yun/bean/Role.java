package com.cloud.yun.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cloud.yun.base.BaseModel;
import lombok.*;

import java.io.Serializable;

/**
 * @ClassName Role
 * @Description Role is to handle xxxx
 * @Author jack
 * @Date 7/12/2022 9:15 AM
 * @Version 1.0
 **/
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Role extends BaseModel implements Serializable {
	private static final long serialVersionUID = 2021916558835768543L;
	@TableId(type = IdType.AUTO)
	private String id;

	private String roleName;

	private String desc;
}
