package com.lai.seckillsystem.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.lai.seckillsystem.entity.Goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品返回對象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsVo extends Goods {

	private BigDecimal seckillPrice;
	private Integer stockCount;
	private Date startDate;
	private Date endDate;

}
