package com.lai.seckillsystem.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共返回的對象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {
	private long code;
	private String message;
	private Object obj;

	/**
	 * 成功返回結果
	 */
	public static RespBean success() {
		return new RespBean(RespBeanEnum.SUCCESS.getCode(),RespBeanEnum.SUCCESS.getMessage(),null);
	}
	
	public static RespBean success(Object object) {
		return new RespBean(RespBeanEnum.SUCCESS.getCode(),RespBean.success().getMessage(),object);
	}
	
	/**
	 * 失敗返回結果
	 */
	public static RespBean error(RespBeanEnum respBeanEnum) {
		return new RespBean(respBeanEnum.getCode(),respBeanEnum.getMessage(),null);
	}
	
	public static RespBean error(RespBeanEnum respBeanEnum,Object object) {
		return new RespBean(respBeanEnum.getCode(),respBeanEnum.getMessage(),object);
	}
	
	
	
}
