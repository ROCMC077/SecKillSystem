package com.lai.seckillsystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lai.seckillsystem.entity.SeckillOrder;
import com.lai.seckillsystem.entity.User;
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
	@Autowired
	private SeckillOrderMapper seckillOrderMapper;
	@Autowired
	private RedisTemplate redisTemplate;
	
	/**
	 * 獲取秒殺結果
	 * @param user
	 * @param goodsId
	 * @return orderID:成功, -1:秒殺失敗, 0:排隊中
	 */
	@Override
	public Integer getResult(User user, Integer goodsId) {
		
		SeckillOrder seckillOrder = seckillOrderMapper.selectOne(new QueryWrapper<SeckillOrder>()
						  							  .eq("user_id",user.getId())
						  							  .eq("goods_id",goodsId));
		if(null!=seckillOrder) {
			return seckillOrder.getOrderId();
		}else if(redisTemplate.hasKey("isStockEmpty:"+goodsId)){
			return -1;
		}else {
			return 0;
		}
	}

}
