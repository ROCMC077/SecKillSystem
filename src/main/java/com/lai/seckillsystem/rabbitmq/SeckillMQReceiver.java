package com.lai.seckillsystem.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.lai.seckillsystem.entity.SeckillMessage;
import com.lai.seckillsystem.entity.SeckillOrder;
import com.lai.seckillsystem.entity.User;
import com.lai.seckillsystem.service.IGoodsService;
import com.lai.seckillsystem.service.IOrderService;
import com.lai.seckillsystem.utils.JsonUtils;
import com.lai.seckillsystem.vo.GoodsVo;

import lombok.extern.slf4j.Slf4j;

/**
 * 消息接收者
 * @author username
 */
@Service
@Slf4j
public class SeckillMQReceiver {
	
	@Autowired
	private IGoodsService goodsService;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private IOrderService orderService;
	
	/**
	 * 下單
	 * @param msg
	 */
	@RabbitListener(queues = "seckillQueue")
	public void receive(String msg) {
		log.info("接收消息：" + msg);
		SeckillMessage seckillMessage = JsonUtils.jsonToObject(msg, SeckillMessage.class);
		Integer goodsId = seckillMessage.getGoodId();
		User user = seckillMessage.getUser();
		
		//檢查庫存
		GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
		if(goodsVo.getStockCount()<1) {
			return;
		}
		
		//判斷是否重複搶購
		SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:"+user.getId()+":"+goodsId);
		if(seckillOrder!=null) {
			return;
		}
		
		//下單
		orderService.seckill(user, goodsVo);
	}
}
