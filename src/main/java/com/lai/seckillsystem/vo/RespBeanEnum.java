package com.lai.seckillsystem.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
/* 公共返回的對象枚舉
 * */
@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {
	//通用
	SUCCESS (200,"SUCCESS"),
	ERROR (500,"ERROR"),
	
	//登入異常
	LOGIN_NULL_ERROR(500210,"手機號碼或密碼為空"),
	MOBILE_ERROR(500211,"手機號碼格式不正確"),
	LOGIN_ERROR(500212,"手機號碼錯誤"),
	LOGIN_PASS_ERROR(500213,"密碼錯誤"),
	BIND_ERROR(500214,"參數校驗異常"),
	
	//秒殺活動
	EMPTY_STOCK(500500,"庫存不足"),
	REPEAT_ERROR(500500,"商品每人限購一件");

	
	private final Integer code;
	private final String message;

}
