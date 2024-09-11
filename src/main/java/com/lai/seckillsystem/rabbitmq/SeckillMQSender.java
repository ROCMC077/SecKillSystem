package com.lai.seckillsystem.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * 消息發送者
 * 
 * @author username
 */
@Service
@Slf4j
public class SeckillMQSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * 發送秒殺訊息
	 * @param msg
	 */
	public void send(String msg) {
		log.info("發送消息：" + msg);
		rabbitTemplate.convertAndSend("seckillExchange", "seckill.message", msg);
	}
}
