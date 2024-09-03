package com.lai.seckillsystem.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lai.seckillsystem.entity.User;
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

}
