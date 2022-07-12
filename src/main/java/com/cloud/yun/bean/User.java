package com.cloud.yun.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.yun.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * @ClassName User
 * @Description User handle to xxx
 * @Author jack
 * @Date 6/23/2022 5:52 PM
 * @Version 1.0
 **/
@Getter
@Setter
@ToString
@AllArgsConstructor
public class User extends BaseModel implements Serializable {
	private static final long serialVersionUID = 8295050210277575080L;

	@TableId(type = IdType.AUTO)
	private Long id;

	private String name;

	private String nickName;

	private String password;

	private String phone;

	private Integer status;   //账户状态   1 可用  2 停用  3 锁定
}
