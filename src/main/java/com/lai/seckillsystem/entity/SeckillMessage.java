package com.lai.seckillsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  rabbitMQ 會用到的訊息
 * @author username
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillMessage {
	private User user;
	private Integer goodId;

}
