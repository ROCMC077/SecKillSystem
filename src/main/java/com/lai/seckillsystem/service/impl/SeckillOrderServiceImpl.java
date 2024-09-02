package com.lai.seckillsystem.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lai.seckillsystem.entity.SeckillOrder;
import com.lai.seckillsystem.mapper.SeckillOrderMapper;
import com.lai.seckillsystem.service.ISeckillOrderService;

/**
 * <p>
 * 秒殺訂單表 serviceImpl
 * </p>
 *
 * @author lai
 */
@Service
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements ISeckillOrderService {

}
