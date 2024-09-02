package com.lai.seckillsystem.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lai.seckillsystem.entity.Order;
import com.lai.seckillsystem.entity.SeckillGoods;
import com.lai.seckillsystem.entity.SeckillOrder;
import com.lai.seckillsystem.entity.User;
import com.lai.seckillsystem.mapper.OrderMapper;
import com.lai.seckillsystem.service.IOrderService;
import com.lai.seckillsystem.service.ISeckillGoodsService;
import com.lai.seckillsystem.service.ISeckillOrderService;
import com.lai.seckillsystem.vo.GoodsVo;

/**
 * <p>
 * 訂單表 serviceImpl
 * </p>
 *
 * @author lai
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

	@Autowired
	private ISeckillGoodsService seckillGoodsService;
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private ISeckillOrderService seckillOrderService;
	
	/**
	 * 秒殺商品購買
	 */
	@Override
	public Order seckill(User user, GoodsVo goods) {
		//秒殺商品表減庫存
		SeckillGoods seckillGoods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>().eq("goods_id", goods.getId()));
		seckillGoods.setStockCount(seckillGoods.getStockCount()-1);
		seckillGoodsService.updateById(seckillGoods);
		//建立訂單
		Order order = new Order();
		order.setUserId(user.getId());
		order.setGoodsId(goods.getId());
		order.setDeliveryAddrId(0);
		order.setGoodsName(goods.getGoodsName());
		order.setGoodsCount(1);
		order.setGoodsPrice(seckillGoods.getSeckillPrice());
		order.setOrderChannel(1);
		order.setStatus(0);
		order.setCreateDate(new Date());
		orderMapper.insert(order);

		//建立秒殺訂單
		SeckillOrder seckillOrder = new SeckillOrder();
		seckillOrder.setUserId(user.getId());
		seckillOrder.setOrderId(order.getId()); 
		seckillOrder.setGoodsId(goods.getId());
		seckillOrderService.save(seckillOrder);
		
		return order;
	}

}
