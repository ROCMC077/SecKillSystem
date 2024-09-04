package com.lai.seckillsystem.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lai.seckillsystem.entity.Order;
import com.lai.seckillsystem.entity.SeckillGoods;
import com.lai.seckillsystem.entity.SeckillOrder;
import com.lai.seckillsystem.entity.User;
import com.lai.seckillsystem.exception.GlobalException;
import com.lai.seckillsystem.mapper.OrderMapper;
import com.lai.seckillsystem.service.IGoodsService;
import com.lai.seckillsystem.service.IOrderService;
import com.lai.seckillsystem.service.ISeckillGoodsService;
import com.lai.seckillsystem.service.ISeckillOrderService;
import com.lai.seckillsystem.vo.GoodsVo;
import com.lai.seckillsystem.vo.OrderDetailVo;
import com.lai.seckillsystem.vo.RespBeanEnum;

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
	@Autowired
	private IGoodsService goodsService;
	@Autowired
	private RedisTemplate redisTemplate;
	
	/**
	 * 秒殺商品購買
	 */
	@Transactional
	@Override
	public Order seckill(User user, GoodsVo goods) {
		//秒殺商品表減庫存
		SeckillGoods seckillGoods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>().eq("goods_id", goods.getId()));
		seckillGoods.setStockCount(seckillGoods.getStockCount()-1);
		//判斷庫存是否大於0
		boolean result = seckillGoodsService.update(new UpdateWrapper<SeckillGoods>()
						   .setSql("stock_count = stock_count-1")
						   .eq("goods_id",goods.getId())
						   .gt("stock_count", 0));
		//建立訂單
		if(!result) {
			return null;
		}
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
		redisTemplate.opsForValue().set("order:"+user.getId()+":"+goods.getId(), seckillOrder);
		return order;
	}

	/**
	 * 訂單詳情
	 */
	@Override
	public OrderDetailVo detail(Integer orderId) {
		if(null==orderId) {
			throw new GlobalException(RespBeanEnum.ORDER_NOT_EXIST);
		}
		
		Order order = orderMapper.selectById(orderId);
		GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(order.getGoodsId());
		OrderDetailVo detailVo = new OrderDetailVo(order,goodsVo);
		return detailVo;
	}

}
