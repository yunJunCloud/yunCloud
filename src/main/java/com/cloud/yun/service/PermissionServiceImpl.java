package com.cloud.yun.service;

import com.cloud.yun.base.BaseServiceImpl;
import com.cloud.yun.bean.Permission;
import com.cloud.yun.mapper.PermissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ClassName PermissionServiceImpl
 * @Description PermissionServiceImpl is to handle xxxx
 * @Author jack
 * @Date 7/12/2022 10:10 AM
 * @Version 1.0
 **/
@Slf4j
@Service
public class PermissionServiceImpl extends BaseServiceImpl<PermissionMapper, Permission>  implements PermissionService{
}
