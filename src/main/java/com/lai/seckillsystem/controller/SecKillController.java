package com.lai.seckillsystem.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.lai.seckillsystem.config.AccessLimit;
import com.lai.seckillsystem.entity.SeckillMessage;
import com.lai.seckillsystem.entity.SeckillOrder;
import com.lai.seckillsystem.entity.User;
import com.lai.seckillsystem.exception.GlobalException;
import com.lai.seckillsystem.rabbitmq.SeckillMQSender;
import com.lai.seckillsystem.service.IGoodsService;
import com.lai.seckillsystem.service.IOrderService;
import com.lai.seckillsystem.service.ISeckillOrderService;
import com.lai.seckillsystem.utils.JsonUtils;
import com.lai.seckillsystem.vo.GoodsVo;
import com.lai.seckillsystem.vo.RespBean;
import com.lai.seckillsystem.vo.RespBeanEnum;

import io.springboot.captcha.ArithmeticCaptcha;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/seckill")
@Slf4j
public class SecKillController implements InitializingBean{
	@Autowired
	private IGoodsService goodsService;
	@Autowired
	private ISeckillOrderService seckillOrderService;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private SeckillMQSender seckillMQSender;
	
	private Map<Integer, Boolean> emptyStockMap = new HashMap<>();
	
	@PostMapping("/{path}/doSeckill")
	@ResponseBody
	public RespBean doSecKill(@PathVariable String path,Integer goodsId,User user) {
		if(null==user) {
			return RespBean.error(RespBeanEnum.SESSION_ERROR);
		}

		ValueOperations valueOperations = redisTemplate.opsForValue();
		
		//判斷路徑
		boolean check = orderService.checkPath(user,goodsId,path);
		if(!check) {
			return RespBean.error(RespBeanEnum.REQUEST_ILLEGAL); 
		}
		
		//判斷是否重複搶購
		SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:"+user.getId()+":"+goodsId);
		
		if(seckillOrder!=null) {
			return RespBean.error(RespBeanEnum.REPEAT_ERROR);
		}
		
		//通過標記,減少redis的訪問
		if(emptyStockMap.get(goodsId)) {
			return RespBean.error(RespBeanEnum.EMPTY_STOCK);
		}
		
		//預減庫存
		Long stock = valueOperations.decrement("seckillGoods:"+goodsId);
		if(stock<0) {
			emptyStockMap.put(goodsId, true);
			valueOperations.increment("seckillGoods:"+goodsId); //目的只是為了在redis中看起來是0, 不然會是-1
			return RespBean.error(RespBeanEnum.EMPTY_STOCK);
		}
		
		SeckillMessage seckillMessage = new SeckillMessage(user,goodsId);
		seckillMQSender.send(JsonUtils.objectToJson(seckillMessage));
		return RespBean.success(0);
	}
	
	/**
	 * 獲取秒殺結果
	 * @param user
	 * @param goodsId
	 * @return orderID:成功, -1:秒殺失敗, 0:排隊中 
	 */
	@GetMapping("/getResult")
	@ResponseBody
	public RespBean getResult(User user, Integer goodsId) {
		if(null==user) {
			return RespBean.error(RespBeanEnum.SESSION_ERROR);
		}
		Integer orderId = seckillOrderService.getResult(user,goodsId);
		return RespBean.success(orderId);
	}
	
	/**
	 * 獲取秒殺網址
	 * @param user
	 * @param goodsId
	 */
	@AccessLimit(second = 5 ,maxCount=5, needLogin=true)
	@GetMapping("/path")
	@ResponseBody
	public RespBean getPath(User user, Integer goodsId, String captcha,HttpServletRequest request) {
		if(null==user) {
			return RespBean.error(RespBeanEnum.SESSION_ERROR);
		}
		boolean check =  orderService.checkCaptcha(user,goodsId,captcha);
		if(!check) {
			return RespBean.error(RespBeanEnum.ERROR_CAPTCHA);
		}
		String str = orderService.createPath(user,goodsId);
		return RespBean.success(str);
	}
	
	/**
	 * 獲取秒殺網址
	 * @param user
	 * @param goodsId
	 */
	@GetMapping("/captcha")
	@ResponseBody
	public void verifyCode(User user, Integer goodsId,HttpServletResponse response) {
		if(null==user) {
			throw new GlobalException(RespBeanEnum.REQUEST_ILLEGAL);
		}
		
		//設置請求頭為輸出圖片的類型
		response.setContentType("image/jpg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		//生成驗證碼, 將結果放入redis
		ArithmeticCaptcha captcha = new ArithmeticCaptcha(130,32,3);
		redisTemplate.opsForValue().set("captcha:"+user.getId()+":"+goodsId,captcha.text(),300,TimeUnit.SECONDS);
		try {
			captcha.out(response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("驗證碼驗證失敗",e.getMessage());
		}		
	}
	
	

	/**
	 * implements InitializingBean
	 * Spring提供的初始化後方法，在當前bean初始化後會被執行
	 * 系統初始化, 把商品庫存加載入 redis
	 * @throws Exception
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		List<GoodsVo> list = goodsService.findGoodsVo();
		if(CollectionUtils.isEmpty(list)) {
			return ;
		}
		list.forEach(goodsVo -> {
			redisTemplate.opsForValue().set("seckillGoods:"+goodsVo.getId(), goodsVo.getStockCount());
			emptyStockMap.put(goodsVo.getId(), false);
		}
		);
	}

}
