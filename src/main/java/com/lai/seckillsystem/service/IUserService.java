package com.lai.seckillsystem.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lai.seckillsystem.entity.User;
import com.lai.seckillsystem.vo.LoginVo;
import com.lai.seckillsystem.vo.RespBean;

/**
 * <p>
 * User Service
 * </p>
 *
 * @author lai
 */
public interface IUserService extends IService<User> {

	/**
	 * 登入
	 * @param loginVo
	 * @param request
	 * @param response
	 * @return
	 */
	RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 根據cookie獲取用戶
	 * @param userTicket
	 * @param request
	 * @param response
	 * @return
	 */
	User getUserByCookie(String userTicket,HttpServletRequest request, HttpServletResponse response);

}
