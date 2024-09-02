package com.lai.seckillsystem.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 訂單表
 * </p>
 *
 * @author lai
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 訂單ID
     */
    private Integer id;

    /**
     * 用戶ID
     */
    private Integer userId;

    /**
     * 商品ID
     */
    private Integer goodsId;

    /**
     * 收貨地址ID
     */
    private Integer deliveryAddrId;

    /**
     * 冗余過來的商品名稱
     */
    private String goodsName;

    /**
     * 商品數量
     */
    private Integer goodsCount;

    /**
     * 商品價格
     */
    private BigDecimal goodsPrice;

    /**
     * 1:pc 2:android 3:ios
     */
    private Integer orderChannel;

    /**
     * 0:新建未付款 1:已付款 2:已發貨 3:已收貨 4:已退款 5:已完成
     */
    private Integer status;

    /**
     * 訂單創建時間
     */
    private Date createDate;

    /**
     * 付款時間
     */
    private Date payDate;


}
