package com.cdu.lys.graduation.types.payment;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author liyinsong
 * @date 2022/3/7 17:31
 */
@Data
public class PayQuery {

    /**
     * 是否支付
     */
    private boolean paid;

    /**
     * 订单号
     */
    @NotBlank
    private String orderNum;
}
