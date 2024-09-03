package com.lai.seckillsystem.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lai.seckillsystem.entity.User;
import com.lai.seckillsystem.exception.GlobalException;
import com.lai.seckillsystem.mapper.UserMapper;
import com.lai.seckillsystem.service.IUserService;
import com.lai.seckillsystem.utils.CookieUtil;
import com.lai.seckillsystem.utils.MD5Util;
import com.lai.seckillsystem.utils.UUIDUtil;
import com.lai.seckillsystem.vo.LoginVo;
import com.lai.seckillsystem.vo.RespBean;
import com.lai.seckillsystem.vo.RespBeanEnum;

/**
 * <p>
 * User serviceImpl
 * </p>
 *
 * @author lai
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RedisTemplate redisTemplate;


	/**
	 * 登入
	 */
	@Override
	public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
		String mobile = loginVo.getMobile();
		String password = loginVo.getPassword();

		// 參數效驗 因為 loginVo 有加上自定義的 annotation(@IsMobile)所以下面的可以不用了
//		if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
//			return RespBean.error(RespBeanEnum.LOGIN_NULL_ERROR);
//		}
//		if(!ValidatorUtil.isMobile(mobile)) {
//			return RespBean.error(RespBeanEnum.MOBILE_ERROR);
//		}

		// 根據手機號碼或取用戶
		User user = userMapper.selectById(mobile);
		if (null == user) {
			throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
		}

		// 判斷加密後的密碼是否正確
		if (!MD5Util.formPassToDBPass(password, user.getSalt()).equals(user.getPassword())) {
			throw new GlobalException(RespBeanEnum.LOGIN_PASS_ERROR);
		}

		// 生成Cookie
		String ticket = UUIDUtil.uuid(); 
//		request.getSession().setAttribute(ticket, user); 如果有spring-session-data-redis, 這行會自動存到redis session裡面,就不需要下面那幾段
		
		//將用戶資料存入redis
		redisTemplate.opsForValue().set("user:"+ticket, user);
		
		CookieUtil.setCookie(request, response, "userTicket", ticket);
		return RespBean.success(ticket);
	}

	/**
	 * 根據cookie獲取用戶
	 */
	@Override
	public User getUserByCookie(String userTicket,HttpServletRequest request, HttpServletResponse response) {
		if(StringUtils.isEmpty(userTicket)) {
			return null;
		}
		User user = (User)redisTemplate.opsForValue().get("user:"+userTicket);
		if(user!=null) {
			CookieUtil.setCookie(request, response, "userTicket", userTicket);
		}
		return user;
	}

}
