package com.lai.seckillsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lai.seckillsystem.entity.Order;
import com.lai.seckillsystem.entity.User;
import com.lai.seckillsystem.vo.GoodsVo;

/**
 * <p>
 * 訂單表 Service
 * </p>
 *
 * @author lai
 */
public interface IOrderService extends IService<Order> {
	/**
	 * 秒殺購買
	 * @param user
	 * @param goods
	 * @return
	 */
	Order seckill(User user, GoodsVo goods);

}
