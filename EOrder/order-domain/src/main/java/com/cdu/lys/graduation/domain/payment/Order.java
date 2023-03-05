package com.cdu.lys.graduation.domain.payment;

import lombok.Data;

/**
 * 订单mock类
 * @author liyinsong
 * @date 2022/3/7 14:20
 */
@Data
public class Order {

    private String orderNum;

    private double orderAmount;

    private double payAmount;
}
