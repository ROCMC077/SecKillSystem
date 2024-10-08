package com.lai.seckillsystem.config;

import com.lai.seckillsystem.entity.User;

/**
 * 
 * @author username
 *
 */
public class UserContext {
	private static ThreadLocal<User> userHolder = new ThreadLocal<>();

	public static void setUser(User user) {
		userHolder.set(user);
	}

	public static User getUser() {
		return userHolder.get();
	}
}
