package com.lai.seckillsystem.vo;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lai.seckillsystem.validator.IsMobile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import utils.ValidatorUtil;

/**
 * 
 * 為了自定義驗證註解寫的 手機號碼格式規則
 * 
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

	private boolean required = false;

	@Override
	public void initialize(IsMobile constraintAnnotation) {
		required = constraintAnnotation.reqired();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (required) {
			return ValidatorUtil.isMobile(value);
		} else {
			if (StringUtils.isEmpty(value)) {
				return true;
			} else {
				return ValidatorUtil.isMobile(value);
			}
		}
	}

}
