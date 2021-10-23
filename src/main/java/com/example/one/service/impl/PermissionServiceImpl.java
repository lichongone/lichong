package com.example.one.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.one.entity.Permission;
import com.example.one.mapper.PermissionMapper;
import com.example.one.service.IPermissionService;
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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}

