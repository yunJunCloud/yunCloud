package com.cloud.yun.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.cloud.yun.constants.FieldConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 已经在basebean中使用了----@TableField---- 完成了自动填充，就不用在使用MyMetaObjectHandler实现自动填充了
 */
@Slf4j
//@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
	/**
	 * 插入时候自动填充
	 * @param metaObject
	 */
	@Override
	public void insertFill(MetaObject metaObject) {
		log.info("start insert fill -------");
		this.setFieldValByName(FieldConstants.CREATE_TIME,new Date(),metaObject);
		this.setFieldValByName(FieldConstants.UPDATE_TIME,new Date(),metaObject);
		this.setFieldValByName(FieldConstants.ISDELETE,0,metaObject);
		//this.setFieldValByName(FieldConstants.CREATOR,1,metaObject); //创建人修改人应该session中获取
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		log.info("start update fill -------");
		this.setFieldValByName(FieldConstants.UPDATE_TIME,new Date(),metaObject);
	}

}
