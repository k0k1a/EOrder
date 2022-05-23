package com.cdu.lys.graduation.domain.payment;

import lombok.Data;

/**
 * 订单中的商品类
 *
 * @author liyinsong
 * @date 2022/3/8 16:10
 */
@Data
public class OrderGoods {
    private Integer goodsId;

    private String goodsPic;

    private String goodsName;

    private double goodsPrice;

    private int goodsQuantity;

    private double extraPrice;

    private String extraOptions;

}
