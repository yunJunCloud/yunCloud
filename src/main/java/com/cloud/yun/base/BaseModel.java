package com.cloud.yun.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName Basebean
 * @Description TODO
 * @Author jack
 * @Date 6/23/2022 5:58 PM
 * @Version 1.0
 **/
@Data
public class BaseModel implements Serializable {

	private static final long serialVersionUID = -5490853502014235792L;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@TableField(value = "create_date",fill = FieldFill.INSERT)
	private Date createDate;

	@DateTimeFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JSONField(
			format = "yyyy-MM-dd HH:mm:ss"
	)
	@TableField(value = "update_date",fill = FieldFill.INSERT_UPDATE)
	private Date updateDate;

	/**
	 * 	逻辑删除（0未删除 1 删除）
	 */
	@TableLogic(value = "0",delval = "1")
	@TableField(value = "is_delete",fill = FieldFill.INSERT)
	private Integer isDelete;

	@Version
	@TableField(value = "version",fill = FieldFill.INSERT_UPDATE)
	private Integer version;
}
