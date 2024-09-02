package com.lai.seckillsystem.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lai.seckillsystem.entity.SeckillGoods;
import com.lai.seckillsystem.mapper.SeckillGoodsMapper;
import com.lai.seckillsystem.service.ISeckillGoodsService;

/**
 * <p>
 * 秒殺商品表 serviceImpl
 * </p>
 *
 * @author lai
 */
@Service
public class SeckillGoodsServiceImpl extends ServiceImpl<SeckillGoodsMapper, SeckillGoods> implements ISeckillGoodsService {

}
