package com.lai.seckillsystem.vo;

import com.lai.seckillsystem.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品詳情返回對象
 * 
 * @author username
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailVo {
	private User user;
	private GoodsVo goodsVo;
	private int seckillStatus;
	private int remainSeconds;
}
