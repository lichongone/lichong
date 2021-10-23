package com.example.one.service.impl;

import com.example.one.entity.Essay;
import com.example.one.mapper.EssayMapper;
import com.example.one.service.IEssayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LC
 * @since 2021-08-07
 */
@Service
public class EssayServiceImpl extends ServiceImpl<EssayMapper, Essay> implements IEssayService {

}
