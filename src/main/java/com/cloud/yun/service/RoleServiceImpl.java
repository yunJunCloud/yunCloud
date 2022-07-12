package com.cloud.yun.service;

import com.cloud.yun.base.BaseServiceImpl;
import com.cloud.yun.bean.Role;
import com.cloud.yun.mapper.RoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ClassName RoleServiceImpl
 * @Description RoleServiceImpl is to handle xxxx
 * @Author jack
 * @Date 7/12/2022 10:09 AM
 * @Version 1.0
 **/
@Slf4j
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService{
}
