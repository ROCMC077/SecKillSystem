package com.lai.seckillsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lai.seckillsystem.entity.SeckillOrder;
import com.lai.seckillsystem.entity.User;

/**
 * <p>
 * 秒殺訂單表 Service
 * </p>
 *
 * @author lai
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {

	/**
	 * 獲取秒殺結果
	 * @param user
	 * @param goodsId
	 * @return orderID:成功, -1:秒殺失敗, 0:排隊中
	 */
	Integer getResult(User user, Integer goodsId);

}
