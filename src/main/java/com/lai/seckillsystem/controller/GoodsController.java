package com.lai.seckillsystem.controller;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lai.seckillsystem.entity.User;
import com.lai.seckillsystem.service.IGoodsService;
import com.lai.seckillsystem.vo.DetailVo;
import com.lai.seckillsystem.vo.GoodsVo;
import com.lai.seckillsystem.vo.RespBean;


@Controller
@RequestMapping("/goods")
public class GoodsController {
	@Autowired
	private IGoodsService goodsService;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private ThymeleafViewResolver thymeleafViewResolver;

	/**
	 * 跳轉商品頁面
	 */
	@GetMapping(value="/toList", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String toList(Model model, User user, HttpServletRequest request, HttpServletResponse response) {
		ValueOperations valueOperations = redisTemplate.opsForValue();
		String html = (String)valueOperations.get("goodsList");
		//從 redis 獲取頁面
		//如果不為空,直接返回 html
		if(!StringUtils.isEmpty(html)) {
			System.out.println("redis get html");
			return html;
		}
		
		model.addAttribute("user", user);
		model.addAttribute("goodsList", goodsService.findGoodsVo());
		
		//從 redis 獲取頁面
		//如果為空,手動渲染頁面,存入redis並返回
		
	    WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
		html = thymeleafViewResolver.getTemplateEngine().process("goodsList",context);
		if(!StringUtils.isEmpty(html)) {
			valueOperations.set("goodsList", html,60,TimeUnit.SECONDS);
			return html;
		}
		return null;
	}
	
	/**
	 * 跳轉商品詳細頁面
	 */
//	@GetMapping(value="/toDetail2/{goodsId}", produces = "text/html;charset=utf-8")
//	@ResponseBody
//	public String toDtail2(@PathVariable Integer goodsId,Model model, User user, HttpServletRequest request, HttpServletResponse response) {
//		ValueOperations valueOperations = redisTemplate.opsForValue();
//		String html = (String)valueOperations.get("goodsDetail:"+goodsId);
//		//從 redis 獲取頁面
//		//如果不為空,直接返回 html
//		if(!StringUtils.isEmpty(html)) {
//			System.out.println("redis get html");
//			return html;
//		}
//		model.addAttribute("user", user);
//		GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
//		Date startDate = goodsVo.getStartDate();
//		Date endDate = goodsVo.getEndDate();
//		Date nowDate = new Date();
//		
//		//秒殺活動狀態默認為0
//		int secKillStatus = 0;
//		//秒殺到數計時
//		int remainSeconds = 0;
//		
//		if(nowDate.before(startDate)) {
//			//秒殺活動未開始
//			remainSeconds = (int)((startDate.getTime() - nowDate.getTime())/1000);
//			
//		}else if (nowDate.after(endDate)) {
//			//秒殺活動已結束
//			secKillStatus = 2;
//			remainSeconds = -1;
//		}else {
//			//秒殺活動進行中
//			secKillStatus = 1;
//			remainSeconds = 0;
//		}
//
//		model.addAttribute("remainSeconds", remainSeconds);
//		model.addAttribute("secKillStatus", secKillStatus);
//		model.addAttribute("goods", goodsVo);
//		
//		//從 redis 獲取頁面
//		//如果為空,手動渲染頁面,存入redis並返回
//				
//		WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
//		html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail",context);
//		if(!StringUtils.isEmpty(html)) {
//			valueOperations.set("goodsDetail:"+goodsId, html,60,TimeUnit.SECONDS);
//			return html;
//		}
//		return null;
//	}
	
	/**
	 * 跳轉商品詳細頁面
	 */
	@GetMapping("/detail/{goodsId}")
	@ResponseBody
	public RespBean toDtail(@PathVariable Integer goodsId,Model model, User user) {
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
		DetailVo detailVo = new DetailVo();
		detailVo.setUser (user);
		detailVo.setGoodsVo(goodsVo);
		detailVo.setSeckillStatus(secKillStatus);
		detailVo.setRemainSeconds(remainSeconds);
		return RespBean.success(detailVo);
	}
	
}
