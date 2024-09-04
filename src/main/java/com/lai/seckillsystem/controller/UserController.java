package com.lai.seckillsystem.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lai.seckillsystem.entity.User;
import com.lai.seckillsystem.rabbitmq.MQSender;
import com.lai.seckillsystem.vo.RespBean;

/**
 * <p>
 * User Controller
 * </p>
 *
 * @author lai
 */
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private MQSender mqSender;
	
	/**
	 * 獲取用戶資料 (用jmeter測試)
	 * @param user
	 * @return
	 */
	@GetMapping("/info")
	@ResponseBody
	public RespBean info(User user) {
		return RespBean.success(user);
	}
	
	
	/**
	 * 測試發送 RabbitMQ 消息
	 * @param user
	 * @return
	 */
	@RequestMapping("/mq")
	@ResponseBody
	public void mq() {
		mqSender.send("Hello");
	}

}
