package com.lai.seckillsystem.exception;
/**
 * 
 * 全局異常
 * 
 * */

import com.lai.seckillsystem.vo.RespBeanEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalException extends RuntimeException{
	private RespBeanEnum respBeanEnum;
}
