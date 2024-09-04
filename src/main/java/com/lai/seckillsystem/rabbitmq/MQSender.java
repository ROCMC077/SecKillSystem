package com.lai.seckillsystem.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * 消息發送者
 * @author username
 */
@Service
@Slf4j
public class MQSender {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send(Object msg) {
		log.info("發送消息：" + msg);
		rabbitTemplate.convertAndSend("queue", msg);
	}

}
