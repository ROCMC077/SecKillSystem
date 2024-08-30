package com.lai.seckillsystem.service;

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
	 * */
	RespBean doLogin(LoginVo loginVo);

}
