package com.lai.seckillsystem.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * RabbitMQ Topic 配置類
 * 
 * @author username
 *
 */
@Configuration
public class RabbitMQSeckillTopicConfig {

	private static final String QUEUQ = "seckillQueue";
	private static final String EXCHANGE = "seckillExchange";

	@Bean
	public Queue queue() {
		return new Queue(QUEUQ);
	}

	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange(EXCHANGE);
	}

	@Bean
	public Binding binding01() {
		return BindingBuilder.bind(queue()).to(topicExchange()).with("seckill.#");
	}

}
