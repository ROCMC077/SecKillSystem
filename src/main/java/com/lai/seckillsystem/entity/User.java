package com.lai.seckillsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * User
 * </p>
 *
 * @author lai
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用戶id,電話號碼
     */
    private Integer id;

    private String nickname;

    /**
     * MD5(MD5(pass明文+固定salt)+salt)
     */
    private String password;

    private String salt;

    /**
     * 頭像
     */
    private String head;

    private Date registerDate;

    private Date lastLoginTime;

    private Integer loginCount;


}
