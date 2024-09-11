package com.lai.seckillsystem.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lai.seckillsystem.config.AccessLimit;
import com.lai.seckillsystem.config.UserContext;
import com.lai.seckillsystem.entity.User;
import com.lai.seckillsystem.service.IUserService;
import com.lai.seckillsystem.utils.CookieUtil;
import com.lai.seckillsystem.vo.RespBean;
import com.lai.seckillsystem.vo.RespBeanEnum;


@Component
public class AccessLimitInterceptor implements HandlerInterceptor {
	@Autowired
	private IUserService userService;
	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			User user = getUser(request, response);
			UserContext.setUser(user);
			HandlerMethod hm = (HandlerMethod) handler;
			AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
			if (null == accessLimit) {
				return true;
			}

			int second = accessLimit.second();
			int maxCount = accessLimit.maxCount();
			boolean needLogin = accessLimit.needLogin();
			String key = request.getRequestURI();
			if(needLogin) {
				if (null==user) {
					render(response,RespBeanEnum.SESSION_ERROR);
					return false;
				}
				key+=":"+user.getId();
			}
			ValueOperations valueOperations = redisTemplate.opsForValue();
			Integer count = (Integer) valueOperations.get(key);
			if(null==count) {
				valueOperations.set(key, 1, second,TimeUnit.SECONDS);
			}else if(count<maxCount) {
				valueOperations.increment(key);
			}else {
				render(response, RespBeanEnum.ACCESS_LITMIT);
				return false;
			}
		}
		return true;
	}
	
	private void render(HttpServletResponse response, RespBeanEnum RespBeanEnum) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		RespBean respBean =  RespBean.error(RespBeanEnum);
		out.write(new ObjectMapper().writeValueAsString(respBean));
		out.flush();
		out.close();
	}

	/**
	 * 獲取當前登入用戶
	 * @param request
	 * @param response
	 * @return
	 */
	public User getUser(HttpServletRequest request, HttpServletResponse response) {

		String ticket = CookieUtil.getCookieValue(request, "userTicket");
		if (StringUtils.isEmpty(ticket)) {
			return null;
		}

		return userService.getUserByCookie(ticket, request, response);

	}
}
