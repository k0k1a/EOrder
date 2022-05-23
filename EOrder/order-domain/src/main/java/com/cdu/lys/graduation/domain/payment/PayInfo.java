package com.cdu.lys.graduation.domain.payment;

import lombok.Data;

/**
 * @author liyinsong
 * @date 2022/3/7 14:39
 */
@Data
public class PayInfo {
    private Order order;

    public PayInfo(Order order) {
        this.order = order;
    }
}
