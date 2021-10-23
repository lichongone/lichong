package com.example.one.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.one.entity.RolePermission;
import com.example.one.mapper.RolePermissionMapper;
import com.example.one.service.IRolePermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LC
 * @since 2021-08-04
 */

@Service
@Transactional
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {

}

