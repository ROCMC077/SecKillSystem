package com.lai.seckillsystem.vo;

import com.lai.seckillsystem.entity.Goods;
import com.lai.seckillsystem.entity.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 訂單詳情返回對象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVo extends Goods {
	private Order order;
	private GoodsVo goodsVo;

}
