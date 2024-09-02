package com.lai.seckillsystem.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lai.seckillsystem.entity.Goods;
import com.lai.seckillsystem.vo.GoodsVo;

/**
 * <p>
 * 商品表 Service
 * </p>
 *
 * @author lai
 */
public interface IGoodsService extends IService<Goods> {
	/**
	 * 獲取商品列表
	 * @return
	 */
	List<GoodsVo> findGoodsVo();
	
	/**
	 * 獲取商品詳細資料
	 * @return
	 */
	GoodsVo findGoodsVoByGoodsId(Integer goodsId);

}
