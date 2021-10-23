package com.example.one.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.one.entity.Role;
import com.example.one.mapper.RoleMapper;
import com.example.one.service.IRoleService;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}

