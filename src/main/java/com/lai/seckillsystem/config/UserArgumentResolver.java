package com.lai.seckillsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lai.seckillsystem.entity.User;
import com.lai.seckillsystem.service.IUserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.CookieUtil;

/**
 * 自定義用戶參數
 */
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
	@Autowired
	private IUserService userService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> clazz = parameter.getParameterType();
		return clazz == User.class;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);

		String ticket = CookieUtil.getCookieValue(request, "userTicket");
		if (StringUtils.isEmpty(ticket)) {
			return null;
		}
		
		return userService.getUserByCookie(ticket, request, response);
	}

}
