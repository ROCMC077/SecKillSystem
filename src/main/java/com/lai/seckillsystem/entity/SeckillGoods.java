package com.lai.seckillsystem.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 秒殺商品表
 * </p>
 *
 * @author lai
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_seckill_goods")
public class SeckillGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 秒殺商品ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 商品ID
     */
    private Integer goodsId;

    /**
     * 秒殺價格
     */
    private BigDecimal seckillPrice;

    /**
     * 庫存數量
     */
    private Integer stockCount;

    /**
     * 秒殺開始時間
     */
    private Date startDate;

    /**
     * 秒殺結束時間
     */
    private Date endDate;


}
