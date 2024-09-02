package com.lai.seckillsystem.exception;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lai.seckillsystem.vo.RespBean;
import com.lai.seckillsystem.vo.RespBeanEnum;

/**
 * 
 * 全局異常處理類
 * 
 * */
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public RespBean ExceptionHandler(Exception e) {
		if(e instanceof GlobalException) {
			GlobalException ex = (GlobalException) e;
			return RespBean.error(ex.getRespBeanEnum());
		}else if( e instanceof BindException) {
			BindException ex = (BindException) e;
			RespBean respBean = RespBean.error(RespBeanEnum.MOBILE_ERROR); //這段有點冗餘, 因為message還是會被清掉
			respBean.setMessage("參數校驗異常: "+ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
			return respBean;
		}
		return RespBean.error(RespBeanEnum.ERROR);
	}

}
