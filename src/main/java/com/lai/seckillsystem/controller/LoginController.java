package com.lai.seckillsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lai.seckillsystem.service.IUserService;
import com.lai.seckillsystem.vo.LoginVo;
import com.lai.seckillsystem.vo.RespBean;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {
	@Autowired
	private IUserService userService;
	
	@RequestMapping("/toLogin")
	public String toLogin() {
		return "login";
	}
	
	@RequestMapping("/doLogin")
	@ResponseBody
	public RespBean doLogin(@Valid LoginVo loginVo) {
//		log.info("{}",loginVo);
		return userService.doLogin(loginVo);
	}
	
}
