package com.lai.seckillsystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lai.seckillsystem.entity.User;
import com.lai.seckillsystem.mapper.UserMapper;
import com.lai.seckillsystem.service.IUserService;
import com.lai.seckillsystem.vo.LoginVo;
import com.lai.seckillsystem.vo.RespBean;
import com.lai.seckillsystem.vo.RespBeanEnum;

import utils.MD5Util;
import utils.ValidatorUtil;

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
	
	/**
	 * 登入
	 * */
	@Override
	public RespBean doLogin(LoginVo loginVo) {
		String mobile = loginVo.getMobile();
		String password = loginVo.getPassword();
		
		//參數效驗 因為有自定義annotation 所以下面的可以不用了
//		if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
//			return RespBean.error(RespBeanEnum.LOGIN_NULL_ERROR);
//		}
//		if(!ValidatorUtil.isMobile(mobile)) {
//			return RespBean.error(RespBeanEnum.MOBILE_ERROR);
//		}
		
		//根據手機號碼或取用戶
		User user = userMapper.selectById(mobile);
		if(null==user) {
			return RespBean.error(RespBeanEnum.LOGIN_ERROR);
		}
		
		//判斷加密後的密碼是否正確
		if(!MD5Util.formPassToDBPass(password, user.getSalt()).equals(user.getPassword())) {
			return RespBean.error(RespBeanEnum.LOGIN_PASS_ERROR);
		}
		return RespBean.success();
	}

}
