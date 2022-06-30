package com.cloud.yun.service;

import com.cloud.yun.base.BaseServiceImpl;
import com.cloud.yun.bean.User;
import com.cloud.yun.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author jack
 * @Date 6/23/2022 6:32 PM
 * @Version 1.0
 **/
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService{
}
