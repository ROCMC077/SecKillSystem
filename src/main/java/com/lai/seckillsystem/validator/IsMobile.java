package com.lai.seckillsystem.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.lai.seckillsystem.vo.IsMobileValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * 
 * 自定義驗證註解-手機號碼格式
 * 
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { IsMobileValidator.class })
public @interface IsMobile {

	boolean reqired() default true;

	String message() default "手機號碼格式錯誤";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
