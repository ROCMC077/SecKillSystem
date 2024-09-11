package com.lai.seckillsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lai.seckillsystem.entity.Order;
import com.lai.seckillsystem.entity.User;
import com.lai.seckillsystem.vo.GoodsVo;
import com.lai.seckillsystem.vo.OrderDetailVo;

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

	
	/**
	 * 訂單詳情
	 * @param orderId
	 * @return
	 */
	OrderDetailVo detail(Integer orderId);
	

	/**
	 * 獲取秒殺網址
	 */
	String createPath(User user, Integer goodsId);

	/**
	 * 校驗秒殺網址
	 * @param user
	 * @param goodsId
	 * @param path 
	 * @return
	 */
	boolean checkPath(User user, Integer goodsId, String path);

	/**
	 * 校驗驗證碼
	 * @param user
	 * @param goodsId
	 * @param path 
	 * @return
	 */
	boolean checkCaptcha(User user, Integer goodsId, String captcha);

}
