package com.cdu.lys.graduation.mini.payment;

import com.cdu.lys.graduation.domain.payment.Order;
import com.cdu.lys.graduation.types.payment.PayQuery;
import com.cdu.lys.graduation.types.payment.PrePayQuery;

/**
 * 支付
 *
 * @author liyinsong
 * @date 2022/3/7 14:58
 */
public interface PaymentService {

    /**
     * 创建订单
     * @param prePayQuery 支付请求
     * @param userId 用户id
     * @return 订单
     */
    Order createOrder(PrePayQuery prePayQuery, Integer userId);

    /**
     *
     * @param payQuery
     * @param userId
     * @return
     */
    boolean pay(PayQuery payQuery, Integer userId);
}
