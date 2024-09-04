package com.lai.seckillsystem.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lai.seckillsystem.entity.User;
import com.lai.seckillsystem.service.IOrderService;
import com.lai.seckillsystem.vo.OrderDetailVo;
import com.lai.seckillsystem.vo.RespBean;
import com.lai.seckillsystem.vo.RespBeanEnum;

/**
 * <p>
 * 訂單表 Controller
 * </p>
 * @author lai
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private IOrderService orderService;
	
	@GetMapping("/detail")
	@ResponseBody
	public RespBean detail(User user, Integer orderId) {
		if(null==user) {
			return RespBean.error(RespBeanEnum.SESSION_ERROR);
		}
		OrderDetailVo detail =  orderService.detail(orderId);
		return RespBean.success(detail);
		
	}

}
