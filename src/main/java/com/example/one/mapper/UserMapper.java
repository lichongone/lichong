package com.example.one.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.one.entity.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LC
 * @since 2021-08-04
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
