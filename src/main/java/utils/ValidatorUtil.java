package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

/**
 * 驗證手機號碼
 * */
public class ValidatorUtil {
	
	private static final Pattern mobile_pattern = Pattern.compile("^1\\d{9}$");
	
	public static boolean isMobile(String mobile) {
		if(StringUtils.isEmpty(mobile)) {
			return false;
		}
		Matcher matcher = mobile_pattern.matcher(mobile);
		return matcher.matches();
	}

}
