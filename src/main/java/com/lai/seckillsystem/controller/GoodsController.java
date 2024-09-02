package com.lai.seckillsystem.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lai.seckillsystem.entity.User;
import com.lai.seckillsystem.service.IGoodsService;
import com.lai.seckillsystem.vo.GoodsVo;

@Controller
@RequestMapping("/goods")
public class GoodsController {
	@Autowired
	private IGoodsService goodsService;

	/**
	 * 跳轉商品頁面
	 */
	@GetMapping("toList")
	public String toList(Model model, User user) {
		model.addAttribute("user", user);
		model.addAttribute("goodsList", goodsService.findGoodsVo());
		return "goodsList";
	}
	
	/**
	 * 跳轉商品詳細頁面
	 */
	@GetMapping("toDetail/{goodsId}")
	public String toDtail(@PathVariable Integer goodsId,Model model, User user) {
		model.addAttribute("user", user);
		GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
		Date startDate = goodsVo.getStartDate();
		Date endDate = goodsVo.getEndDate();
		Date nowDate = new Date();
		
		//秒殺活動狀態默認為0
		int secKillStatus = 0;
		//秒殺到數計時
		int remainSeconds = 0;
		
		if(nowDate.before(startDate)) {
			//秒殺活動未開始
			remainSeconds = (int)((startDate.getTime() - nowDate.getTime())/1000);
			
		}else if (nowDate.after(endDate)) {
			//秒殺活動已結束
			secKillStatus = 2;
			remainSeconds = -1;
		}else {
			//秒殺活動進行中
			secKillStatus = 1;
			remainSeconds = 0;
		}

		model.addAttribute("remainSeconds", remainSeconds);
		model.addAttribute("secKillStatus", secKillStatus);
		model.addAttribute("goods", goodsVo);
		return "goodsDetail";
	}
}
