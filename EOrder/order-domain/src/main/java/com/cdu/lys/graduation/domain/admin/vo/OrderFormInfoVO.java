package com.cdu.lys.graduation.domain.admin.vo;

import lombok.Data;

/**
 * @author liyinsong
 * @date 2022/3/22 23:37
 */
@Data
public class OrderFormInfoVO {

    private Integer id;

    private String orderNum;

    private String goodsName;

    private Double goodsPrice;

    private Integer goodsQuantity;

    private String extraOptions;

    private String deliveryStatus;
}
