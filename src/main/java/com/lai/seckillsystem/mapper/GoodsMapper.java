package com.lai.seckillsystem.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lai.seckillsystem.entity.Goods;
import com.lai.seckillsystem.vo.GoodsVo;

/**
 * <p>
 * 商品表 Mapper
 * </p>
 *
 * @author lai
 */
public interface GoodsMapper extends BaseMapper<Goods> {

	List<GoodsVo> findGoodsVo();

	GoodsVo findGoodsVoByGoodsId(Integer goodsId);

}
