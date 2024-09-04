package com.lai.seckillsystem.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 *  RabbitMQ 配置類
 * @author username
 *
 */
@Configuration
public class RabbitMQConfig {
	
	@Bean
	public Queue queue() {
		return new Queue("queue",true);
	}
}
