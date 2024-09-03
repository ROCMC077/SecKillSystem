package com.lai.seckillsystem.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.lai.seckillsystem.validator.IsMobile;

import lombok.Data;

@Data
public class LoginVo {
	
	@NotNull
	@IsMobile
	private String mobile;
	
	@NotNull
	@Length(min = 32)
	private String password;  // MD5 加密32位元
}
