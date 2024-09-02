package com.lai.seckillsystem.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author lai
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_goods")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 商品名稱
     */
    private String goodsName;

    /**
     * 商品標題
     */
    private String goodsTitle;

    /**
     * 商品圖片
     */
    private String goodsImg;

    /**
     * 商品細節
     */
    private String goodsDetail;

    /**
     * 商品價格
     */
    private BigDecimal goodsPrice;

    /**
     * 商品庫存,-1表示沒有限制
     */
    private Integer goodsStock;


}
