package com.example.one.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.one.entity.User;
import com.example.one.mapper.UserMapper;
import com.example.one.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LC
 * @since 2021-08-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
