package com.lai.seckillsystem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lai.seckillsystem.entity.Goods;
import com.lai.seckillsystem.mapper.GoodsMapper;
import com.lai.seckillsystem.service.IGoodsService;
import com.lai.seckillsystem.vo.GoodsVo;

/**
 * <p>
 * 商品表 serviceImpl
 * </p>
 *
 * @author lai
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {
	@Autowired
	private GoodsMapper goodsMapper;

	@Override
	public List<GoodsVo> findGoodsVo() {
		return goodsMapper.findGoodsVo();
	}

	@Override
	public GoodsVo findGoodsVoByGoodsId(Integer goodsId) {
		return goodsMapper.findGoodsVoByGoodsId(goodsId);
	}

}
