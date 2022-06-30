package com.cloud.yun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.yun.bean.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName UserMapper
 * @Description TODO
 * @Author jack
 * @Date 6/23/2022 6:19 PM
 * @Version 1.0
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
