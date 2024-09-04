package com.lai.seckillsystem.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * 消息接收者
 * @author username
 */
@Service
@Slf4j
public class MQReceiver {
	
	@RabbitListener(queues = "queue")
	public void receive(Object msg) {
		log.info("接收消息：" + msg);
	}
}
