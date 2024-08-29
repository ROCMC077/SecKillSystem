package com.lai.seckillsystem.service.impl;

import com.lai.seckillsystem.entity.User;
import com.lai.seckillsystem.mapper.UserMapper;
import com.lai.seckillsystem.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * User serviceImpl
 * </p>
 *
 * @author lai
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
