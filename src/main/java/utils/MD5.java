package utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class MD5 {

	public static final String salt = "seckill";
	
	public static String md5(String str) {
		return DigestUtils.md5Hex(str);
	}
	
	//第一次加密 由前端輸入密碼到後端
	public static String inputPassToFormPass(String inputPass) {
		String str = salt.charAt(1)+salt.charAt(3)+salt.charAt(5)+inputPass+salt.charAt(0)+salt.charAt(2)+salt.charAt(4);
		return md5(str);
	}
	
	//第二次加密 由加密後的密碼 再加密一次到資料庫
	public static String formPassToDBPass(String formPass,String salt) {
		String str = salt.charAt(1)+salt.charAt(3)+salt.charAt(5)+formPass+salt.charAt(0)+salt.charAt(2)+salt.charAt(4);
		return md5(str);
	}
	
	public static String inputPassToDBPass(String inputPass,String salt) {
		String formPass = inputPassToFormPass(inputPass);
		String dbPass = formPassToDBPass(formPass,salt);
		return dbPass;
	}
	
	public static void main(String[] args) {
		System.out.println(inputPassToFormPass("123456"));
		System.out.println(formPassToDBPass("71a5999d4ce625987dc0abb2bd43c096",salt));
		System.out.println(inputPassToDBPass("123456",salt));

	}

}
