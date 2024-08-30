package com.lai.seckillsystem.vo;

import org.hibernate.validator.constraints.Length;

import com.lai.seckillsystem.validator.IsMobile;

import jakarta.validation.constraints.NotNull;
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
