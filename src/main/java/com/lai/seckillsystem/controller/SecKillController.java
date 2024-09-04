package com.lai.seckillsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lai.seckillsystem.entity.Order;
import com.lai.seckillsystem.entity.SeckillOrder;
import com.lai.seckillsystem.entity.User;
import com.lai.seckillsystem.service.IGoodsService;
import com.lai.seckillsystem.service.IOrderService;
import com.lai.seckillsystem.service.ISeckillOrderService;
import com.lai.seckillsystem.vo.GoodsVo;
import com.lai.seckillsystem.vo.RespBean;
import com.lai.seckillsystem.vo.RespBeanEnum;

@Controller
@RequestMapping("/seckill")
public class SecKillController {
	@Autowired
	private IGoodsService goodsService;
	@Autowired
	private ISeckillOrderService seckillOrderService;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private RedisTemplate redisTemplate;
	
//	@PostMapping("/doSeckill2")
//	public String doSecKill2(Integer goodsId,User user,Model model) {
//		if(null==user) {
//			return "login";
//		}
//		model.addAttribute("user",user);
//		
//		//判斷庫存
//		GoodsVo goods = goodsService.findGoodsVoByGoodsId(goodsId);
//		if(goods.getStockCount()<1) {
//			model.addAttribute("errmsg",RespBeanEnum.EMPTY_STOCK.getMessage());
//			return "seckillFail";
//		}
//		
//		//判斷是否重複搶購
//		SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id",user.getId()).eq("goods_id", goodsId));
//		if(seckillOrder!=null) {
//			model.addAttribute("errmsg",RespBeanEnum.REPEAT_ERROR.getMessage());
//			return "seckillFail";
//		}
//		
//		Order order = orderService.seckill(user,goods);
//		
//		model.addAttribute("order",order);
//		model.addAttribute("goods",goods);
//
//		return "orderDetail";
//	}
	
	@PostMapping("/doSeckill")
	@ResponseBody
	public RespBean doSecKill(Integer goodsId,User user,Model model) {
		if(null==user) {
			return RespBean.error(RespBeanEnum.SESSION_ERROR);
		}
		
		//判斷庫存
		GoodsVo goods = goodsService.findGoodsVoByGoodsId(goodsId);
		if(goods.getStockCount()<1) {
			model.addAttribute("errmsg",RespBeanEnum.EMPTY_STOCK.getMessage());
			return RespBean.error(RespBeanEnum.EMPTY_STOCK);
		}
		
		//判斷是否重複搶購
		SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:"+user.getId()+":"+goods.getId());
		
		if(seckillOrder!=null) {
			return RespBean.error(RespBeanEnum.REPEAT_ERROR);
		}
		
		Order order = orderService.seckill(user,goods);
		

		return RespBean.success(order);
	}

}
